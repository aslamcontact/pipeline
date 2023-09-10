pipeline
        {
            agent {
                docker {
                    image 'aslamimages/mvn_jdk_git:latest'
                    args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
                }
                }

            options {
                skipDefaultCheckout()
            }

     
            stages{

                 stage('test1')
                         {
                             agent {
                                 docker { image 'aslamimages/alpine-git:2'
                                     args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker\' -v ${pwd}/git -w /git'
                                 }
                             }

                            steps {


                                        sh 'pwd'
                                        sh 'ls'


                                   }
                         }
                stage('test2')
                        {
                            agent {
                                docker { image 'aslamimages/alpine-git:2'
                                    args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker\' -v ${pwd}/git -w /git'
                                }
                            }


                            steps {

                                sh 'pwd'
                                sh 'git --version'


                            }
                        }

                 }

        }
