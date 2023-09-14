
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
                deployImage="aslamimages/basic_api"

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
                                        "docker build -t ${deployImage}:${BUILD_NUMBER} ci_api_test/."
                            }
                        }
                stage('deploy')
                        {

                            steps {

                                withCredentials([usernamePassword(credentialsId: 'dockerhub_key',
                                        usernameVariable: 'USERNAME',
                                        passwordVariable: 'PASSWORD')]
                                ) {

                                    sh 'docker compose /home/basic_api/down'

                                    sh 'docker login --username $USERNAME --password $PASSWORD'
                                    sh "docker tag ${deployImage}:${BUILD_NUMBER} ${deployImage}:latest"
                                    sh "docker push ${deployImage}:${BUILD_NUMBER}"
                                    sh "docker push ${deployImage}:latest"

                                }



                            }
                        }





            }

            post{

                always{
                    sh "docker volume rm ${volume}"
                    sh 'docker compose /home/basic_api/up -d'
                }
                success{
                     sh " docker image rm "+
                         "\$(docker images | awk '{print \$1 \" \" \$2 \" \" \$3}' "+
                             "| grep ${deployImage} | grep -v latest "+
                             "| grep -v '${deployImage} ${BUILD_NUMBER}' | awk '{print \$3}')"
                }

                }

        }
