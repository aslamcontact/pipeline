pipeline
        {
            agent {
                      docker {
                                image 'ubuntu:latest'
                                args ' -v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
                            }
                    }
            tools{
                maven 'maven'
            }
            options {
                skipDefaultCheckout()
            }
            environment{
                         myname='aslam'
                         con=false
                       }
     
            stages{

                 stage('test1')
                         {


                            steps {

                         
                                   sh 'mvn -v'
                                

                                   }
                         }



                 }


        }
