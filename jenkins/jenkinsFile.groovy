pipeline
        {
            agent {
                      docker {
                                image 'aslamimages/alpine-git:2'
                                args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker -v ${PWD}/git -w /git'
                                reuseNode true
                            }
                    }

            options {
                skipDefaultCheckout()
            }

     
            stages{

                 stage('test1')
                         {

                            steps {

                                        sh 'git  --version'
                                        sh 'ls'


                                   }
                         }
                stage('test2')
                        {

                            steps {

                                sh 'pwd'


                            }
                        }

                 }

        }
