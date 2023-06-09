package generateConfigurationFiles;

import model.DeploymentApp;
import model.DeploymentDB;
import model.Docker;
import model.Secret;
import model.ServiceClusterIp;
import model.ServiceLoadBalancer;
import model.ServiceNodePort;
import model.Storage;

public class GenerateConfigurationFilesApp {

	public static void main(String[] args) {

		//Création d'un fichier de configuration secret.yml
		//Paramètres: nom du secret et le mot de passe 
		Secret secret=new Secret("mysql-secret", "test1234");
		secret.createSecretFile();
		
		
		//Création d'un fichier de configuration storage.yaml
		//Paramètres: nom du volume, nom du volume claim, capacité et répertoire de stockage
		Storage storage=new Storage("mysql-pv-volume", "mysql-pv-claim", "20Gi", "/mnt/data");
		storage.createStorageFile();
		
		
		//Création d'un fichier de configuration deploymentDB.yaml => Pour les bases de données
		//Paramètres: nom du déploiement,nom de image,instance de la classe Secret, répertoire de stockage, port, instance de la classe Storage
		DeploymentDB depDB=new DeploymentDB("mysql", "mysql:5.7", secret, "/var/lib/mysql", 3306, storage);
		depDB.createDeploymentDBFile();
		
		
		//Création d'un fichier de configuration deploymentApp.yaml => Pour les applications avec un replicas par défaut: 1
		//Paramètres: nom du déploiement et nom de image
		DeploymentApp depApp=new DeploymentApp("my-service", "efrei/my-service:latest");
		depApp.createDeploymentAppFile();
		
		
		//Création d'un fichier de configuration deploymentApp.yaml => Pour les applications avec un nombre de replicas donné
		//Paramètres: nom du déploiement, nom de image et le nombre de replicas
		DeploymentApp depApp2=new DeploymentApp("my-service", "efrei/my-service:latest",2);
		depApp2.createDeploymentAppFile();
		
		
		//Création d'un fichier de configuration serviceClusterIp.yaml pour les applications
		//Paramètres: instance de la classe DeploymentApp et port
		ServiceClusterIp serviceClusterIpApp=new ServiceClusterIp(depApp, 3306);
		serviceClusterIpApp.createServiceClusterIpFileApp();
		
		
		//Création d'un fichier de configuration serviceClusterIp.yaml pour les bases de données
		//Paramètres: instance de la classe DeploymentDB et port
		ServiceClusterIp serviceClusterIpDB=new ServiceClusterIp(depDB, 3306);
		serviceClusterIpDB.createServiceClusterIpFileDB();
		
		
		//Création d'un fichier de configuration serviceNodePort.yaml avec un Nodeport par défaut: 31281 pour les applications
		//Paramètres: instance de la classe DeploymentApp et port
		ServiceNodePort serviceNodePortApp=new ServiceNodePort(depApp2, 3306);
		serviceNodePortApp.createServiceNodePortFileApp();
		
		
		//Création d'un fichier de configuration serviceNodePort.yaml avec un NodePort donné pour les applications
		//Paramètres: instance de la classe DeploymentApp, port et nodePort
		ServiceNodePort serviceNodePortApp2=new ServiceNodePort(depApp2, 3306, 31283);
		serviceNodePortApp2.createServiceNodePortFileApp();
	

		//Création d'un fichier de configuration serviceNodePort.yaml avec un Nodeport par défaut: 31290 pour les DB
		//Paramètres: instance de la classe DeploymentDB et port
		ServiceNodePort serviceNodePortDB=new ServiceNodePort(depDB, 3306);
		serviceNodePortDB.createServiceNodePortFileDB();
		
		
		//Création d'un fichier de configuration serviceNodePort.yaml avec un NodePort donné pour les DB
		//Paramètres: instance de la classe DeploymentDB, port et nodePort
		ServiceNodePort serviceNodePortDB2=new ServiceNodePort(depDB, 3306, 31283);
		serviceNodePortDB2.createServiceNodePortFileDB();
		
		
		//Création d'un fichier de configuration serviceLoadBalancer.yaml avec un Nodeport par défaut: 31285 pour les applications
		//Paramètres: instance de la classe DeploymentApp et port
		ServiceLoadBalancer serviceLoadBalancerApp=new ServiceLoadBalancer(depApp2, 8080);
		serviceLoadBalancerApp.createServiceLoadBalancerFile();
		
		
		//Création d'un fichier de configuration serviceClusterNodePort.yaml avec un NodePort donné pour les applications
		//Paramètres: instance de la classe DeploymentApp, port et nodePort
		ServiceLoadBalancer serviceLoadBalancerApp2=new ServiceLoadBalancer(depApp2, 8080, 31289);
		serviceLoadBalancerApp2.createServiceLoadBalancerFile();
		
		
		//Création d'un fichier  Dockerfile avec une version de java par défaut: 8
		//Paramètres: nom du jar et port
		Docker dockerfile1=new Docker("MyService-0.0.1-SNAPSHOT.jar", 8080);
		dockerfile1.createDockerFile();
		
		
		//Création d'un fichier de configuration Dockerfile avec une version de java donnée
		//Paramètres: nom du jar, port et une version de java
		Docker dockerfile2=new Docker("MyService-0.0.1-SNAPSHOT.jar", 8080, 11);
		dockerfile2.createDockerFile();
		
		
		//Création d'un fichier  Dockerfile avec une version de java et un port par défaut: 8 et 8080
		//Paramètres: nom du jar
		Docker dockerfile3=new Docker("MyService-0.0.1-SNAPSHOT.jar");
		dockerfile3.createDockerFile();
		
		
		//Création d'un fichier de configuration Dockerfile avec une version de java donnée et un port par défaut 8080
		//Paramètres: Une version de java et nom du jar
		Docker dockerfile4=new Docker(11,"MyService-0.0.1-SNAPSHOT.jar");
		dockerfile4.createDockerFile();
		
	}

}
