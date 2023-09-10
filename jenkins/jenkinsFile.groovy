pipeline
        {
            agent {
                docker {
                    image 'ubuntu:latest'
                    args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
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
                                sh "docker volume create ${volume}"
                                sh "docker run --rm -v ${volume}:/app -w /app  --name test2 ${gitImage} git clone ${gitProjectUrl}"
                                sh 'apt install git'
                            }
                        }

                stage('validate')
                        {
                            steps {
                                sh "docker run --rm -v ${volume}:/app -w /app/ci_api_test --name sys ${buildImage} mvn validate"
                            }
                        }
                stage('compile')
                        {
                            steps {
                                sh "docker run --rm -v ${volume}:/app -w /app/ci_api_test --name sys ${buildImage} mvn compile"

                            }
                        }


                stage('package')
                        {
                            steps {
                                sh "docker run --rm -v ${volume}:/app -w /app/ci_api_test --name sys ${buildImage} mvn package"
                            }
                        }

                stage('copy')
                        {
                            steps {
                                sh "docker run --rm -v ${volume}:/app -v /home/buildImage/:/api -w /app/ci_api_test/target --name sys2 ${buildImage} cp ci_test_api-0.0.1-SNAPSHOT.jar /api"
                            }
                        }
            }
        }

            post{

                always{sh "docker volume rm ${volume}"}

                }

        }
