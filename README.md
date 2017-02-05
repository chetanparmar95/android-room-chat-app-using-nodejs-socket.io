# android-room-chat-app-using-nodejs-socket.io
this is android room chat application that uses socket.io 
The server is written in nodejs that uses socket.io

<b>Chat app</b> is nodejs project which handles the messages.</br>
<b>RoomChat</b> is android project 

Here, i'll show you how to deploy nodejs project on aws server.

Screen shot of chat screen.</br>
<img src="/aws screenshots/Screenshot_2017-02-04-22-14-11-181_com.chetan.roomchat.app.png" width="400" alt="chat screen">
</br>
Amazon web service allows us to create free tier account which is free for 1 year with limited usage.<br>

First, you need to create an account in aws. <a href="https://www.amazon.com/ap/signin?openid.assoc_handle=aws&openid.return_to=https%3A%2F%2Fsignin.aws.amazon.com%2Foauth%3Fresponse_type%3Dcode%26client_id%3Darn%253Aaws%253Aiam%253A%253A015428540659%253Auser%252Fhomepage%26redirect_uri%3Dhttps%253A%252F%252Fconsole.aws.amazon.com%252Fconsole%252Fhome%253Fstate%253DhashArgs%252523%2526isauthcode%253Dtrue%26noAuthCookie%3Dtrue&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&action=&disableCorpSignUp=&clientContext=&marketPlaceId=&poolName=&authCookies=&pageId=aws.ssop&siteState=registered%2Cen_US&accountStatusPolicy=P1&sso=&openid.pape.preferred_auth_policies=MultifactorPhysical&openid.pape.max_auth_age=120&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&server=%2Fap%2Fsignin%3Fie%3DUTF8&accountPoolAlias=&forceMobileApp=0&language=en_US&forceMobileLayout=0">create an account</a>
</br>

after creating an account, <a href="https://www.amazon.com/ap/signin?openid.assoc_handle=aws&openid.return_to=https%3A%2F%2Fsignin.aws.amazon.com%2Foauth%3Fresponse_type%3Dcode%26client_id%3Darn%253Aaws%253Aiam%253A%253A015428540659%253Auser%252Fhomepage%26redirect_uri%3Dhttps%253A%252F%252Fconsole.aws.amazon.com%252Fconsole%252Fhome%253Fstate%253DhashArgs%252523%2526isauthcode%253Dtrue%26noAuthCookie%3Dtrue&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&action=&disableCorpSignUp=&clientContext=&marketPlaceId=&poolName=&authCookies=&pageId=aws.ssop&siteState=registered%2Cen_US&accountStatusPolicy=P1&sso=&openid.pape.preferred_auth_policies=MultifactorPhysical&openid.pape.max_auth_age=120&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&server=%2Fap%2Fsignin%3Fie%3DUTF8&accountPoolAlias=&forceMobileApp=0&language=en_US&forceMobileLayout=0">login</a> to the aws console.</br>

<img src="/aws screenshots/1.png" width="600"></br></br>

After login, you will see this screen.<br>

<img src="/aws screenshots/2.png" width="600"></br></br>


click on the services on top left corner of the screen, you will get the list of services aws provides,<br>
<img src="/aws screenshots/3.png" width="600"></br></br>

Under Compute, there is EC2(Elastic compute) option, its an instance that aws provides, where you can do anything. As it is free tier account you will get limited storage. Click on EC2<br>
<img src="/aws screenshots/4.png" width="600"></br></br>

Click on launch instance:<br>
You are now in the EC2 Launch Instance Wizard, which will help you configure and launch your instance.<br>

With Amazon EC2, you can specify the software and specifications of the instance you want to use. In this screen, you are shown options to choose an Amazon Machine Image (AMI), which is a template that contains the software configuration<br>
select : Ubuntu Server 16.04 LTS (HVM), SSD Volume Type - ami-7c803d1c which is free tier eligible
<img src="/aws screenshots/5.png" width="600"></br></br>

You will now choose an instance type. Instance types comprise of varying combinations of CPU, memory, storage, and networking capacity so you can choose the appropriate mix for your applications.<br>
The default option of t2.micro should already be checked.  This instance type is covered within the Free Tier<br>
<img src="/aws screenshots/6.png" width="600"></br></br>

 You can review the configuration, storage. tagging and security settings that have been selected for your instance. While you have the option to customize these settings, we recommend accepting the default values for this tutorial.<br>
click on Launch at the bottom of the page.</br>
<img src="/aws screenshots/11.png" width="600"></br></br>


On the next screen you will be asked to choose an existing key pair or create a new key pair. A key pair is used to log into your instance (just like your house key is used to enter your home). Select Create a new key pair and give it the name cloudProject. Next click the Download Key Pair button. Be sure to save the key pair in a safe location on your computer.</br>
<img src="/aws screenshots/12.png" width="600"></br>


Note: If you don't remember where you store your SSH private key (the file you are downloading), you won't be able to connect to your virtual machine.</br></br></br>


Click View Instances on the next screen to view your instances and see the status of the instance you have just started.</br>
<img src="/aws screenshots/15.png" width="600"></br>

Make note of the Public IP address of your AWS instance, you will need this to connect to the instance</br></br>


<h3>Connecting to instance using Terminal</h3>
windows user: Download Git for Windows <a href="https://git-scm.com/download/win">here</a>. Run the downloaded installer accepting the default settings (this will install Git Bash as part of Git).</br>
Right click on your desktop (not on an icon or file) and select Git Bash Here to open a Git Bash command prompt.
<br><br>

Use SSH to connect to your instance. In this case the user name is ubuntu, the SSH key is stored in the directory we saved it to in previous steps, and IP address you will get from instance details. The format is ssh -i {full path of your .pem file} ubuntu@{instance IP address}.
<br><br>

Windows users: Enter ssh -i 'c:\Users\chetan\.ssh\cloudProject.pem' ubuntu@{IP_Address} (ex. ssh -i 'c:\Users\adamglic\.ssh\cloudProject.pem' ubuntu@52.27.212.125)<br><br>
Mac/Linux users: Enter ssh -i ~/.ssh/cloudProject.pem ubuntu@{IP_Address} (ex. ssh -i ~/.ssh/cloudProject.pem ubuntu@52.27.212.125)<br><br>


You'll see a response similar to the following:<br><br>

The authenticity of host 'ec2-198-51-100-1.compute-1.amazonaws.com (10.254.142.33)' can't be established. RSA key fingerprint is 1f:51:ae:28:df:63:e9:d8:cf:38:5d:87:2d:7b:b8:ca:9f:f5:b1:6f. Are you sure you want to continue connecting (yes/no)?<br><br>

Type <b>yes</b> and press enter.<br><br>

<h3><Terminate Your Instance/h3>

Back on the EC2 Console, select the box next to the instance you created.  Then click the Actions button, navigate to Instance State, and click Terminate.<br><br>

You will be asked to confirm your termination - select Yes, Terminate.<br><br>

Now your instance is ready to use. you can install any software you want. we need to run node project in this instance so we will install apache for http request and nodejs.<br><br>

in you terminal, use this command:<br>
$sudo apt-get install apache<br>
$sudo apt-get install nodejs<br>
$sudo apt-get install npm<br>

now you can upload the project file on ubuntu server<br>
I'd recommend to upload using filezilla. <a href="http://angus.readthedocs.io/en/2014/amazon/transfer-files-between-instance.html">here</a> is the tutorial<br><br><br>

Once it is uploaded, run the node project using following command:<br>
$sudo nodejs index.js

<br><br><br><br>





