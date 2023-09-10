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
                                     image 'ubuntu:latest'
                                     args '-v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
                                 }
                             }


                             steps {


                                        sh 'pwd'
                                        sh 'docker run -itd --name test1 alpine'
                                        sh 'docker ps'
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
