pipeline
        {
            agent {
                      docker {
                                image 'node:18.17.1-alpine3.18'
                                args ' -v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
                            }
                    }

            options {
                skipDefaultCheckout()
            }

     
            stages{

                 stage('test1')
                         {

                            steps {
                                        sh 'node --version'
                                        sh 'pwd'
                                        sh 'ls'
                                        sh 'who'
                                        sh 'cd ../../'
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
