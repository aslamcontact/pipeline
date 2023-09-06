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
                             sh 'apt install -y git'

                         withCredentials([usernamePassword(credentialsId: 'docker_hub_access',
                                 usernameVariable: 'USERNAME',
                                 passwordVariable: 'PASSWORD')]
                         ) {

                             sh 'docker login --username $USERNAME --password $PASSWORD'
                         }


                     }
                 }




                 }




        }
