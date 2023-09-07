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
            environment{
                         myname='aslam'
                         con=false
                       }
     
            stages{

                 stage('test1') {
                       when{
                            expression{con==true}
                       }

                     steps {

                         
                            sh 'echo $myname'
                                

                           }
                                }

                 stage('test2'){
                     steps{
                            sh "echo 'test2'"
                         }

                               }


                 }


        }
