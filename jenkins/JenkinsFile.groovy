
pipeline
        {
            agent {
                docker {
                    image 'ubuntu:latest'
                    args ' -v /var/run/docker.sock:/var/run/docker.sock ' +
                            ' -v /usr/bin/docker:/usr/bin/docker'
                       }
                }


            options {
                skipDefaultCheckout()
            }

            environment{
                volume='artifact'
                gitImage='aslamimages/alpine-git:2'
                buildImage="aslamimages/mvn_jdk_git:latest"
                gitProjectUrl="https://github.com/aslamcontact/ci_api_test.git"

                      }
            stages {

                stage('cloning')
                        {
                            steps {
                                  sh  "docker volume create ${volume}"

                                  sh  "docker run --rm  --name test2 "+
                                          "-v ${volume}:/app "+
                                          "-w /app  ${gitImage} "+
                                          "git clone ${gitProjectUrl}"
                                   }
                        }

                stage('validate')
                        {
                            steps {
                                sh "docker run --rm --name sys "+
                                        "-v ${volume}:/app "+
                                        " -w /app ${buildImage} "+
                                        "mvn -f ci_api_test/ validate"
                            }
                        }
                stage('compile')
                        {
                            steps {
                                sh "docker run --rm  --name sys "+
                                        "-v ${volume}:/app "+
                                        "-w /app ${buildImage} " +
                                        "mvn -f ci_api_test/ compile"

                            }
                        }


                stage('package')
                        {
                            steps {
                                sh "docker run --rm  --name sys "+
                                        "-v ${volume}:/app "+
                                        "-w /app ${buildImage} " +
                                        "mvn -f ci_api_test/ package"
                            }
                        }

                stage('build')
                        {
                            steps {
                                sh "docker run --rm --name sys2 "+
                                        " -v ${volume}:/app "+
                                        " -v /var/run/docker.sock:/var/run/docker.sock "+
                                        " -v /usr/bin/docker:/usr/bin/docker "+
                                        " -w /app  ubuntu:latest "+
                                        "docker build -t aslamimages/basic_api:${BUILD_NUMBER} ci_api_test/."
                            }
                        }
                stage('deploy')
                        {

                            steps {

                                sh "docker tag aslamimages/basic_api:${BUILD_NUMBER} aslamimages/basic_api:latest"
                                sh "docker push aslamimages/basic_api:${BUILD_NUMBER}"
                                sh "docker push aslamimages/basic_api:latest"


                            }
                        }





            }

            post{

                always{
                    sh "docker volume rm ${volume}"
                    sh "docker images"
                }

                }

        }
