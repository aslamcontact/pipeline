pipeline
        {
            agent {
                      docker {
                                image 'alpine/git:latest'
                                args '-it --entrypoint /bin/sh -v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker '
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
                                sh 'ls'
                                sh 'who'

                            }
                        }

                 }

        }
