pipeline
        {
            agent {
                      docker {
                                image 'ubuntu:latest'
                                args ' -v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
                            }
                    }
     
            stages{

                 stage('test') {
                     steps {

                         

                                

                           }
                                }


                 }


        }
