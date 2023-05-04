<?php

namespace App\Controller;

use App\Entity\Personne;
use App\Repository\PersonneRepository;
use Exception;
use PhpParser\Node\Expr\Cast\Object_;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoder;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

class PersonneJsonController extends AbstractController
{

    #[Route('/personne/json', name: 'app_personne_json')]
    public function index(): Response
    {
        return $this->render('personne_json/index.html.twig', [
            'controller_name' => 'PersonneJsonController',
        ]);
    }
#[Route('/signin', name: 'app_signin_json')]
    public function signin(Request $req,UserPasswordEncoderInterface $PasswordEncoder)
    {
$nomPers=$req->query->get('nomPers');
$prenomPers=$req->query->get('prenomPers');
$numTel=$req->query->get('numTel');
$email=$req->query->get('email');
$mdp=$req->query->get('mdp');
$adresse=$req->query->get('adresse');
$rib=$req->query->get('rib');
$role=$req->query->get('role');
if(!filter_var($email,FILTER_VALIDATE_EMAIL)){
return new Response("email invalide.");
}
$student=new Personne();
$student->setNomPers($nomPers);
$student->setPrenomPers($prenomPers);
$student->setNumTel($numTel);
$student->setEmail($email);
$student->setMdp(
    $PasswordEncoder->encodePassword(
        $student,
        $mdp
    )
);
$student->setAdresse($adresse);
$student->setRib($rib);
$student->setRole($role);
        $student->setToken("");
        $student->setDate(new \DateTime('now'));
        $student->setVerified(false);
        try{
            $em = $this->getDoctrine()->getManager();
        $em->persist($student); 
        $em->flush();
        return new JsonResponse("compte crée.",200); //http result 200
        }catch(\Exception $ex)
        {
            return new Response("exception".$ex->getMessage());
        }
    }

    #[Route('/login', name: 'app_login_json')]
    public function login(Request $req,UserPasswordEncoderInterface $PasswordEncoder)
    {
$email=$req->query->get('email');
$mdp=$req->query->get('mdp');
$em = $this->getDoctrine()->getManager();
$user=$em->getRepository(Personne::class)->findOneBy(['email'=>$email]);
if($user){
    if(password_verify($mdp,$user->getMdp())){
        $serializer=new Serializer([new ObjectNormalizer()]);
        $formatted=$serializer->normalize($user);
        return new JsonResponse($formatted); 
    }else{
        return new Response("mot de passe incorrecte");
    }
}
else{
    return new Response("Utilisateur inexistant");
}
    }




    #[Route('/All', name: 'listepersonne')]
    public function getStudents(PersonneRepository $repo, SerializerInterface $serializer)
    {
        $students = $repo->findAll();
        //* Nous utilisons la fonction normalize qui transforme le tableau d'objets 
        //* students en  tableau associatif simple.
        // $studentsNormalises = $normalizer->normalize($students, 'json', ['groups' => "students"]);

        // //* Nous utilisons la fonction json_encode pour transformer un tableau associatif en format JSON
        // $json = json_encode($studentsNormalises);

        $json = $serializer->serialize($students, 'json');

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
        return new Response($json);
    }

    #[Route("/Student/{idPers}", name: "student")]
    public function StudentId($idPers, NormalizerInterface $normalizer, PersonneRepository $repo)
    {
        $student = $repo->find($idPers);
        $studentNormalises = $normalizer->normalize($student, 'json');
        return new Response(json_encode($studentNormalises));
    }


    #[Route("/addStudentJSON/new", name: "addStudentJSON")]
    public function addStudentJSON(Request $req,   NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $student = new Personne();
        $student->setNomPers($req->get('nomPers'));
        $student->setPrenomPers($req->get('prenomPers'));
        $student->setNumTel($req->get('numTel'));
        $student->setEmail($req->get('email'));
        $student->setMdp($req->get('mdp'));
        $student->setAdresse($req->get('adresse'));
        $student->setRib($req->get('rib'));
        $student->setRole($req->get('role'));
        $student->setToken("");
        $student->setDate(new \DateTime('now'));
        $student->setVerified(0);
        $em->persist($student);
        $em->flush();

        $jsonContent = $Normalizer->normalize($student, 'json');
        return new Response(json_encode($jsonContent));
    }

    

    #[Route("/updateStudentJSON/{idPers}", name: "updateStudentJSON")]
    public function updateStudentJSON(Request $req, $idPers, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $student = $em->getRepository(Personne::class)->find($idPers);
        $student->setNomPers($req->get('nomPers'));
        $student->setPrenomPers($req->get('prenomPers'));
        $student->setNumTel($req->get('numTel'));
        $student->setEmail($req->get('email'));
        $student->setMdp($req->get('mdp'));
        $student->setAdresse($req->get('adresse'));
        $student->setRib($req->get('rib'));
        $student->setRole($req->get('role'));
        $em->flush();

        $jsonContent = $Normalizer->normalize($student, 'json');
        return new Response("Student updated successfully " . json_encode($jsonContent));
    }

    #[Route("/deleteStudentJSON/{idPers}", name: "deleteStudentJSON")]
    public function deleteStudentJSON(Request $req, $idPers, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $student = $em->getRepository(Personne::class)->find($idPers);
        $em->remove($student);
        $em->flush();
        $jsonContent = $Normalizer->normalize($student, 'json');
        return new Response("Student deleted successfully " . json_encode($jsonContent));
    }
  
}
