pipeline
        {
            agent {
                      docker {
                                image 'node:18.17.1-alpine3.18'
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