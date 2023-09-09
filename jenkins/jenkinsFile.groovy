pipeline
        {
            agent {
                      docker {
                                image 'alpine/git:latest'
                                args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker  --entrypoint sh '
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
