<?php

namespace App\Controller;

use App\Entity\Personne;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use App\Form\PersonneType;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use DateTime;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;


use Swift_Mailer;
use Swift_Message;
use Swift_SmtpTransport;
use Swift_Image;
use Symfony\Component\HttpFoundation\JsonResponse;

#[Route('/personne')]
class PersonneController extends AbstractController
{


    #[Route('/index', name: 'app_personne_index', methods: ['GET', 'POST'])]
    public function index(EntityManagerInterface $entityManager,PaginatorInterface $paginator,Request $request, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
       

        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);

        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');
        $evenements = $personneRepository->findAll();
        $imgev = [];

        foreach ($evenements as $event) {
            $imgev[$event->getIdPers()] = $imagePersRepository->findBy(['idPers' => $event->getIdPers()]);
        }
        $search = $request->query->get('search1');
        $queryBuilder = $entityManager
            ->getRepository(Personne::class)
            ->createQueryBuilder('p')
            ;
        $filter = null;
        $filter = $request->query->get('inputfilter');
       if ($filter) {
            $personnes = $personneRepository->getAllByPersonneRole($filter);
        } else {
            $personnes = $personneRepository->findAll();
        }
        $query = $personnes;
        
    
$pagination = $paginator->paginate(
        $query,
        $request->query->getInt('page', 1),
        8
    );
        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        } else {
            if ($personne->getRole() == "admin") {
                return $this->render('templates_back/personne/index.html.twig', [
                    'search' => $search,
                    'Img' => $imgev,
                    'evenements' => $evenements,
                    'personnes' => $pagination,
                    'personne' => $personne,
                    'last' => $last,
                    'customproducts' => $pagination,
                ]);
            } else {
                return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
            }
        }
    }
    #[Route('/stat', name: 'app_trans_stat', methods: ['GET'])]
    public function statindex(SessionInterface $session,ImagePersRepository $imagePersRepository, Request $request,EntityManagerInterface $entityManager,FlashyNotifier $flashy ): Response
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);

        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');
        $flashy->info('Statistic !!!!');

        $sumQueryBuilder = $entityManager
        ->createQueryBuilder()
        ->select('p.role as material, SUM(p) as weight_sum')
        ->from(Personne::class, 'p') 
        ->groupBy('p.role');

        $weightSums = $sumQueryBuilder->getQuery()->getResult();

        // Transform the results into a format that Chart.js can use
        $weightData = [
            'labels' => [],
            'datasets' => [
                [
                    'label' => 'role',
                    'data' => [],
                    'backgroundColor' => 'rgba(54, 162, 235, 0.2)',
                    'borderColor' => 'rgba(54, 162, 235, 1)',
                    'borderWidth' => 1
                ]
            ]
        ];

        foreach ($weightSums as $weightSum) {
            $weightData['labels'][] = $weightSum['material'];
            $weightData['datasets'][0]['data'][] = $weightSum['weight_sum'];
        }

        return $this->render('templates_back/personne/stat.html.twig', [
            'weightData' => $weightData,
            'weightSums' => $weightSums,
            'personne' => $personne,
                    'last' => $last,
        ]);

    }
   
    #[Route('/', name: 'app_personne_accueil', methods: ['GET'])]
    public function acceuil(): Response
    {
        return $this->render('templates_front/personne/accueil.html.twig');
    }
    #[Route('/activation', name: 'app_personne_activation', methods: ['GET', 'POST'])]
    public function activation(FlashyNotifier $flashy, Request $request, SessionInterface $session, PersonneRepository $personneRepository): Response
    {
        $personne = $session->get('id');
        $personne->setIs_verified(1);
        $personneRepository->update($personne, true);
        $flashy->success('email vérifié avec succès');
        return $this->renderForm('templates_front/personne/activation.html.twig');
    }
    #[Route('/signin', name: 'app_personne_signin', methods: ['GET', 'POST'])]
    public function signin(Request $request, Swift_Mailer $mailer, PersonneRepository $personneRepository, SessionInterface $session, TokenGeneratorInterface $tokenGenerator, UserPasswordEncoderInterface $PasswordEncoder): Response
    {
        $personne = new Personne();
        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $password = $personne->getMdp();

            $personne->setMdp($PasswordEncoder->encodePassword(
                $personne,
                $password
            ));

            $token = $tokenGenerator->generateToken();
            try {
                $personne->setToken($token);
                $personneRepository->save($personne, true);
            } catch (\Exception $exception) {
                return $this->renderForm('templates_front/personne/signin.html.twig', [
                    'personne' => $personne,
                    'form' => $form,
                ]);
            }
            $session->set('id', $personne);
            $session->set('personne', $personne->getIdPers());
            $url = $this->generateUrl('app_personne_activation', array('token' => $token), UrlGeneratorInterface::ABSOLUTE_URL);

            // Create the Transport
            $transport = (new Swift_SmtpTransport('smtp.gmail.com', 465, 'ssl'))
                ->setUsername('djaguo12@gmail.com')
                ->setPassword('BACMATH2K20');

            // Create the Mailer using your created Transport
            $mailer = new Swift_Mailer($transport);

            // Create a message
            $message = (new Swift_Message('Activation de Compte'))
                ->setFrom(['djaguo12@gmail.com' => 'Evëntili'])
                ->setTo([$personne->getEmail()])
                ->setBody("<p>Salut! </p>Veuillez cliquer: " . $url, 'text/html');

// Joindre une image à l'e-mail
$image = Swift_Image::fromPath('C:\xampp\htdocs\img\wedding.png');
$image1 = Swift_Image::fromPath('C:\xampp\htdocs\img\logo.png');
$body = '<img src="' . $message->embed($image1) . '" style="max-width:29%;height:auto;">';
$body .= '<img src="' . $message->embed($image) . '" style="max-width:100%;height:auto;">';
$body .= '<br><br><br>';
$body .= '<p style="font-family: Comic Sans MS, cursive;margin-left: 288px;">Veuillez cliquer sur le bouton ci-dessous pour le reset de mot de passe :</p>';
$body .= '<a href="' . $url . '" style="display:inline-block;background-color:#f58922;margin-left: 398px;color:#fff;padding:19px 35px;text-decoration:none;border-radius:5px;">Reset pass</a>';

// Ajouter l'image au contenu du message
$message->setBody($body, 'text/html');

            // Send the message
            $result = $mailer->send($message);
            return $this->redirectToRoute('app_imagepers_add', [], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('templates_front/personne/signin.html.twig', [
            'personne' => $personne,
            'form' => $form,
        ]);
    }


    #[Route('/editprofil', name: 'app_personne_editprofil', methods: ['GET', 'POST'])]
    public function editprofil(Request $request, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }

        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $personneRepository->modifyPersonne($idPerss, $personne);
            return $this->redirectToRoute('app_personne_change', [], Response::HTTP_SEE_OTHER);
        }

        if (!$personne) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        } else {
            if ($personne->getRole() == "organisateur" || $personne->getRole() == "partenaire") {
                return $this->renderForm('templates_front/personne/editp.html.twig', [
                    'personne' => $personne,
                    'form' => $form,
                    'last' => $last,
                ]);
            } else {
                return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
            }
        }
    }
    #[Route('/changep', name: 'app_personne_change', methods: ['GET', 'POST'])]
    public function change(Request $request, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $user = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $mdp1 = $request->request->get('new_password');

        $user1 = $personneRepository->findemm($user->getEmail(), $user->getMdp());
        if (!empty($mdp1)) {
            if ($user1 != null) {

                $user->setMdp($mdp1);
                $user1->setMdp($mdp1);
                $session->set('id', $user1);
                $personneRepository->update($user, true);


                return $this->redirectToRoute('app_imagepers_modifier', [], Response::HTTP_SEE_OTHER);
            }
        }
        if (!$user) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        } else {
            if ($user->getRole() == "organisateur" || $user->getRole() == "partenaire") {
                return $this->render('templates_Front/personne/change.html.twig', [
                    'personne' => $user,
                    'last' => $last,
                ]);
            } else {
                return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
            }
        }
    }
    #[Route('/deconnection', name: 'app_personne_deconnection', methods: ['GET', 'POST'])]
    public function deconnection(SessionInterface $session): Response
    {
        $session->remove('idPer');
        $session->remove('personne');
        $session->clear();
        return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/verif', name: 'app_personne_verif', methods: ['GET', 'POST'])]
    public function verif(FlashyNotifier $flashy, Request $request, PersonneRepository $personneRepository, SessionInterface $session, UserPasswordEncoderInterface $passwordEncoder): Response
    {
        if ($request->isMethod('POST')) {
            if (isset($_POST['g-recaptcha-response'])) {
                $recaptcha = $_POST['g-recaptcha-response'];
                if (!$recaptcha) {
                    return $this->renderForm('templates_Front/personne/login.html.twig');
                }
                $mdp = $request->request->get('mdp');
                $email = $request->request->get('email');
                $personne = new Personne();
                $personne = $personneRepository->findOneBy(['email' => $email]);
                if (!$personne) {
                    $flashy->error('email ou  mot de passe incorrect');
                    return $this->renderForm('templates_front/personne/login.html.twig');
                }
                if (!$passwordEncoder->isPasswordValid($personne, $mdp)) {
                    $flashy->error('email ou  mot de passe incorrect');
                    return $this->renderForm('templates_front/personne/login.html.twig');
                }
                if ($personne->getIs_verified() == 0) {
                    $flashy->warning('email non vérifié');
                    return $this->renderForm('templates_front/personne/login.html.twig');
                }
                $date = new DateTime();
                $personne->setDate($date);
                $personneRepository->update($personne, true);
                $session->set('id', $personne);
                $session->set('personne', $personne->getIdPers());
                if ($personne->getRole() != "admin")
                    return $this->redirectToRoute('app_imagepers_affich', [], Response::HTTP_SEE_OTHER);
                else
                    return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
            }
        }
        return $this->renderForm('templates_front/personne/login.html.twig');
    }



    #[Route('/new', name: 'app_personne_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $user = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);

        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');

        $personne = new Personne();
        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $personneRepository->save($personne, true);
            $idPers = $personne->getIdPers();
            $session->set('idPer', $idPers);
            // $imageUploadUrl = $router->generate('app_imagepers_new', ['idPers' => $personne->getIdPers()]);
            return $this->redirectToRoute('app_imagepers_new', [], Response::HTTP_SEE_OTHER);
        }

        if (!$user) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        } else {
            if ($user->getRole() == "admin") {
                return $this->renderForm('templates_back/personne/new.html.twig', [
                    'personne' => $user,
                    'form' => $form,
                    'last' => $last,
                ]);
            } else {
                return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
            }
        }
    }

    #[Route('/{idPers}', name: 'app_personne_show', methods: ['GET'])]
    public function show(Personne $personne, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $personne1 = $session->get('id');
        $idPers = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPers]);
        $images = array_reverse($images);
        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        if ($personne1->getRole() == "admin") {
            return $this->render('templates_back/personne/show.html.twig', [
                'personne' => $personne,
                'last' => $last,
            ]);
        } else {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        }
    }

    #[Route('/{idPers}/edit', name: 'app_personne_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Personne $personne, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session): Response
    {
        $personne1 = $session->get('id');
        $idPers = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPers]);
        $images = array_reverse($images);
        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }

        $form = $this->createForm(PersonneType::class, $personne);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $personneRepository->save($personne, true);

            return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
        }
        if (!$personne1) {
            return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
        } else {
            if ($personne1->getRole() == "admin") {
                return $this->renderForm('templates_back/personne/edit.html.twig', [
                    'personne' => $personne1,
                    'personne1' => $personne,
                    'form' => $form,
                    'last' => $last,
                ]);
            } else {
                return $this->redirectToRoute('app_personne_accueil', [], Response::HTTP_SEE_OTHER);
            }
        }
    }

    #[Route('/{idPers}', name: 'app_personne_delete', methods: ['POST'])]
    public function delete(Request $request, Personne $personne, PersonneRepository $personneRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $personne->getIdPers(), $request->request->get('_token'))) {
            $personneRepository->remove($personne, true);
        }

        return $this->redirectToRoute('app_personne_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/searchCustom', name: 'app_personne_search', methods: ['GET'])]
    public function searchCustomPersonne(Request $request, EntityManagerInterface $entityManager): JsonResponse
    {
        $searchValue = $request->query->get('searchValue');

        $queryBuilder = $entityManager
            ->getRepository(Personne::class)
            ->createQueryBuilder('c')
            ->where('c.nom LIKE :search')
            ->setParameter('search', '%' . $searchValue . '%');

        $customProducts = $queryBuilder->getQuery()->getResult();

        $result = [];
        foreach ($customProducts as $customProduct) {
            $result[] = [
                'nom' => $customProduct->getNom(),
                'prenom' => $customProduct->getPrenom(),
                'num' => $customProduct->getNum(),
                'email' => $customProduct->getEmail(),
                'adresse' => $customProduct->getAdresse(),
                'role' => $customProduct->getRole(),
            ];
        }

        return new JsonResponse($result);
    }
}
