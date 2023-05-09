<?php

namespace App\Controller;

use App\Entity\Imagepers;
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
use Swift_Mailer;
use Swift_Message;
use Swift_SmtpTransport;
use Swift_Image;

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
    public function signin(Request $req, UserPasswordEncoderInterface $PasswordEncoder)
    {
        $nomPers = $req->query->get('nomPers');
        $prenomPers = $req->query->get('prenomPers');
        $numTel = $req->query->get('numTel');
        $email = $req->query->get('email');
        $mdp = $req->query->get('mdp');
        $adresse = $req->query->get('adresse');
        $rib = $req->query->get('rib');
        $role = $req->query->get('role');
        if (preg_match('/\d/', $nomPers)) {
            return new Response("Erreur : le nom ne doit pas contenir de chiffres !");
        }
        if (preg_match('/\d/', $prenomPers)) {
            return new Response("Erreur : le prénom ne doit pas contenir de chiffres !");
        }
        if (!preg_match("/^[0-9]{8}$/", $numTel)) {
            return new Response("Erreur : le numéro est invalide !");
        }
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            return new Response("erreur.");
        }
        if (strlen($mdp) < 8) {
            return new Response("Erreur : le mot de passe est invalide !");
        }
        if (!preg_match('/^\d{20}$/', $rib)) {
            return new Response("Erreur : le rib est invalide !");
        }
        $student = new Personne();
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
        try {
            $em = $this->getDoctrine()->getManager();
            $em->persist($student);
            $em->flush();
            return new JsonResponse("compte crée.", 200); //http result 200
        } catch (\Exception $ex) {
            return new Response("exception" . $ex->getMessage());
        }
    }



    #[Route('/sendMail', name: 'app_sendMail_json', methods: ['GET', 'POST'])]
    public function sendMail(PersonneRepository $repo, Request $req, SerializerInterface $serializer)
    {
        $em = $this->getDoctrine()->getManager();
        $email = $req->get('email');
        $personne = $em->getRepository(Personne::class)->findOneBy(['email' => $email]);

        if ($personne == null) {
              return new JsonResponse(['error' => 'L\'adresse e-mail n\'a pas été trouvée.']);
        } else {
            $code = rand(100000, 999999); // génère un nombre aléatoire entre 100000 et 999999
            $code = str_pad($code, 6, '0', STR_PAD_LEFT);
            $personne->setToken($code);
            $em->persist($personne);
            $em->flush();
            $email = $transport = (new Swift_SmtpTransport('smtp.gmail.com', 465, 'ssl'))
                ->setUsername('yesmineguesmi@gmail.com')
                ->setPassword('oyjdjatabndjaaxg');

            // Create the Mailer using your created Transport
            $mailer = new Swift_Mailer($transport);

            // Create a message
            $message = (new Swift_Message('Activation de Compte'))
                ->setFrom(['yesmineguesmi@gmail.com' => 'Evëntili'])
                ->setTo([$personne->getEmail()])
                ->setBody("<p>Salut! </p>Voila le code: " . $code, 'text/html');

            // Joindre une image à l'e-mail
            $image = Swift_Image::fromPath('C:\xampp\htdocs\img\wedding.png');
            $image1 = Swift_Image::fromPath('C:\xampp\htdocs\img\logo.png');
            $body = '<img src="' . $message->embed($image1) . '" style="max-width:29%;height:auto;">';
            $body .= '<img src="' . $message->embed($image) . '" style="max-width:100%;height:auto;">';
            $body .= '<br><br><br>';
            $body .= '<p style="font-family: Comic Sans MS, cursive;margin-left: 288px;">Voici le code pour l"activation de compte : ' . $code . '</p>';

            // Ajouter l'image au contenu du message
            $message->setBody($body, 'text/html');

            // Send the message
            $result = $mailer->send($message);
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize("email envoyé");
            return new JsonResponse($formatted);
        }
    }



    #[Route('/verifcode', name: 'app_verifcode_json')]
    public function verifcode(PersonneRepository $repo, Request $req, SerializerInterface $serializer)
    {
        $token = $req->query->get('token');
        $email = $req->query->get('email');
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(Personne::class)->findOneBy(['email' => $email]);

        if ($user != null) {
            if ($user->getToken() == $token) {
                return new Response("cbon : code w mail correcte !");
            }else{
                 return new JsonResponse(['error' => 'code incorrecte.']);
            }
        } else {
            return new JsonResponse(['error' => 'L\'adresse e-mail n\'a pas été trouvée.']);
        }

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON

    }
    #[Route('/changer', name: 'app_changer_json')]
    public function changer(PersonneRepository $repo, Request $req, SerializerInterface $serializer, UserPasswordEncoderInterface $PasswordEncoder)
    {
        $mdp = $req->query->get('mdp');
        $email = $req->query->get('email');
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(Personne::class)->findOneBy(['email' => $email]);

        if ($user != null) {
            if (strlen($mdp) > 8) {
                $user->setMdp(
                    $PasswordEncoder->encodePassword(
                        $user,
                        $mdp
                    )
                );
                $em->persist($user);
               $em->flush();
               return new Response("cbon : tbadel !");
            }
            else{
                return new JsonResponse(['error' => 'veuillez mettre un mot de passe plus long.']);
                //return new Response("Erreur : veuillez mettre un mot de passe plus long !");
            }
        } else {
            return new JsonResponse(['error' => 'email incorrecte.']);
        }


    }

    #[Route('/getcode', name: 'app_code_json')]
    public function coode(PersonneRepository $repo, Request $req, SerializerInterface $serializer)
    {
        $email = $req->query->get('email');
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(Personne::class)->findOneBy(['email' => $email]);

        if ($user != null) {
            $id = $user->getIdPers();
            $json = $serializer->serialize($id, 'json');

            //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
            return new Response($json);
        } else {
            return new Response("Erreur : aucun utilisateur trouvé !");
        }

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON

    }



    #[Route('/login', name: 'app_login_json')]
    public function login(Request $req, UserPasswordEncoderInterface $PasswordEncoder)
    {
        $email = $req->query->get('email');
        $mdp = $req->query->get('mdp');
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(Personne::class)->findOneBy(['email' => $email]);
        if ($user) {
            if (password_verify($mdp, $user->getMdp())) {
                $serializer = new Serializer([new ObjectNormalizer()]);
                $formatted = $serializer->normalize($user);
                return new JsonResponse($formatted);
            } else {
                return new Response("mot de passe incorrecte");
            }
        } else {
            return new Response("Utilisateur inexistant");
        }
    }




    #[Route('/All', name: 'listepersonne')]
    public function getStudents(PersonneRepository $repo, SerializerInterface $serializer)
    {
        $students = $repo->findAll();
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
    #[Route("/addImageJSON", name: "addImageJSON")]
    public function addImageJSON(Request $req,   NormalizerInterface $Normalizer, PersonneRepository $repo)
    {
        $pers = $repo->find($req->get('idPers'));
        $em = $this->getDoctrine()->getManager();
        $student = new Imagepers();
        $student->setIdPers($pers);
        $student->setLast($req->get('last'));
        $student->setImagep($req->get('imagep'));

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
