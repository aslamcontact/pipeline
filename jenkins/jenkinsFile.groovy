pipeline
        {
            agent {
                      docker {
                                image 'alpine/git:latest'
                                args ' -v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
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
                                        sh 'apk add git'
                                        sh 'git  clone https://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@github.com/aslamcontact/test.git'
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
