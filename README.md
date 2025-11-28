## Working model of save/edit/delete/search using java 21, Document DB, Fargate, Jenkins, ECR, Docker, Ansible, Terraform

📌 Create DocumentDB(MongoDb) Cluster with 1 ec2 instance -- Create Ec2 Bastion Node to connect MongoDb from local client -- DocumentDB uses SSL=true so Client should used valid certificate for handshake. ** ==> Bastion Node Tunling => ssh -i keyPair.pem ubuntu@13.233.124.84 -L 27017:docdb-2025-11-17-15-45-22.cluster-crweyyisomb8.ap-south-1.docdb.amazonaws.com:27017 -N

📌 To Check db connection establishment from bastion Node => nc -vz docdb-2025-11-17-15-45-22.crweyyisomb8.ap-south-1.docdb.amazonaws.com 27017 ==> Connection to docdb-2025-11-17-15-45-22.crweyyisomb8.ap-south-1.docdb.amazonaws.com (172.31.40.181) 27017 port [tcp/*] succeeded! **

📌 SSL HandShake errors -- To show what certificates is being expected at host:port ** ==> openssl s_client -connect localhost:27017 -showcerts output: subject=CN=docdb-2025-11-17-15-45-22.crweyyisomb8.ap-south-1.docdb.amazonaws.com, OU=RDS, O=Amazon.com, L=Seattle, ST=Washington, C=US issuer=C=US, O=Amazon Web Services, Inc., OU=Amazon RDS, ST=WA, CN=Amazon RDS ap-south-1 Subordinate CA RSA2048 G1.A.3, L=Seattle

📌 Copy start and end block from above and paste it "doc-dbIntermidiate.pem" file and execute below: ** ==> keytool -import -trustcacerts -alias docdbCA -file doc-dbIntermidiate.pem -keystore C:\Users\aman.goel1\docdb-truststore.jks -storepass changeit

📌 aman.goel1@IHSMARKIT-fYyFC MINGW64 /c/Learning/Projects/java-spring-apps (develop) $ mvn wrapper:wrapper

