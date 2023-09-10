pipeline
        {


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
                                        sh 'mvn --version'


                                   }
                         }
                stage('test2')
                        {
                            agent {
                                docker { image 'aslamimages/alpine-git:2'
                                         args '-v ${pwd}/git -w /git'
                                       }
                                  }

                            steps {

                                sh 'pwd'
                                sh 'git --version'


                            }
                        }

                 }

        }
