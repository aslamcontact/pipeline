pipeline
        {
            agent none

            options {
                skipDefaultCheckout()
            }

     
            stages{

                 stage('test1')
                         {
                             agent {
                                 docker {
                                     image 'aslamimages/mvn_jdk_git:latest'
                                     args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
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

                                }
                            }


                            steps {

                                sh 'pwd'
                                sh 'git --version'


                            }
                        }

                 }

        }
