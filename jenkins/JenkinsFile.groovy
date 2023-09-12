
pipeline
        {
            agent {
                docker {
                    image 'ubuntu:latest'
                    args    ' -v /var/run/docker.sock:/var/run/docker.sock "+' +
                            ' -v /usr/bin/docker:/usr/bin/docker'
                    args '-v /home/buildImage/:/buildImage'

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
                                        " -w /app/ci_api_test ${buildImage} "+
                                        "mvn validate"
                            }
                        }
                stage('compile')
                        {
                            steps {
                                sh "docker run --rm  --name sys "+
                                        "-v ${volume}:/app "+
                                        "-w /app/ci_api_test ${buildImage} " +
                                        "mvn compile"

                            }
                        }


                stage('package')
                        {
                            steps {
                                sh "docker run --rm --name sys "+
                                        "-v ${volume}:/app "+
                                        "-w /app/ci_api_test  ${buildImage}"+
                                        " mvn package"
                            }
                        }

                stage('copy')
                        {
                            steps {
                                sh "docker run --rm -v ${volume}:/app "+
                                        "-v /home/buildImage/:/buildImage"+
                                        " -w /app/ci_api_test/target "+
                                        "--name sys2 alpine:latest "+
                                        "cp ci_test_api-0.0.1-SNAPSHOT.jar /buildImage/product.jar"
                            }
                        }
                stage('build')
                        {

                            steps {

                                sh "docker build -t jdk_test:${BUILD_NUMBER} /buildImage"

                                sh "docker tag jdk_test:${BUILD_NUMBER} jdk_test:latest"
                            }
                        }





            }

            post{

                always{sh "docker volume rm ${volume}"}

                }

        }
