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

                 stage('test1')
                         {



                             steps {


                                        sh 'pwd'
                                        sh 'docker run --rm -it --name test1 aslamimages/alpine-git:2 sh'
                                        sh 'git --version'
                                        sh "sleep 10"


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
                                sh 'sleep 10'


                            }
                        }

                 }

        }
