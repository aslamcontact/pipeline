pipeline
        {
            agent {
                      docker {
                                image 'ubuntu:latest'
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

                                        sh 'ls'

                                   }
                         }
                stage('test2')
                        {

                            steps {

                                sh 'ls'

                            }
                        }

                 }

        }
