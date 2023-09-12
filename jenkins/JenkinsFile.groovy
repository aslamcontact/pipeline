
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
                vsort="docker images | grep jdk_test |grep -v latest|sort -r -n"
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
                                        "docker build -t jdk_test:${BUILD_NUMBER} ci_api_test/."
                            }
                        }
                stage('show')
                        {

                            steps {

                                sh "echo ${vsort}"


                            }
                        }





            }

            post{

                always{sh "docker volume rm ${volume}"}

                }

        }
