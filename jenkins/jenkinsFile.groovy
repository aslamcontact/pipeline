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

     
            stages{

                 stage('cloning')
                         {
                           environment{
                               volume='artifact'
                           }


                             steps {


                                        sh "docker volume create ${volume}"
                                        sh "docker run --rm -v ${volume}:/app -w /app  --name test2 aslamimages/alpine-git:2 git clone https://github.com/aslamcontact/ci_api_test.git"
                                        sh ''



                                   }
                         }
                stage('test2')
                        {
                            agent {
                                docker { image 'aslamimages/alpine-git:2'

                                }
                            }


                            steps {

                                sh 'pwd'
                                sh 'git --version'
                                sh 'ls'


                            }
                        }

                 }

        }
