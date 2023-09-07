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
                       }
     
            stages{

                 stage('test') {
                     steps {

                         
                            sh 'echo $myname'
                                

                           }
                                }


                 }


        }
