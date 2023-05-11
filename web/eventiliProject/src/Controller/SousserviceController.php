<?php

namespace App\Controller;
use App\Entity\Sousservice;
use App\Entity\Imagess;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\ImagePersRepository;
use App\Form\SousserviceType;
use App\Form\SousserviceType_edit;
use App\Repository\SousserviceRepository;
use App\Repository\ServiceRepository;
use App\Repository\CategEventRepository;
use App\Repository\ImagessRepository;
use App\Repository\PersonneRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;

#[Route('/sousservice')]
class SousserviceController extends AbstractController
{
//affichage des sousservices -----------------------------------------------------------------------------------------------------------------
    #[Route('/', name: 'app_sousservice_index', methods: ['GET'])]
    public function index(
        SousserviceRepository $SousserviceRepository,
        ServiceRepository $ServRepository,
        CategEventRepository $CategEventRepository,
        Request $request,
        SessionInterface $session,
        ImagePersRepository $imagePersRepository,
        ImagessRepository $ImagessRepository,
        PaginatorInterface $paginator,
        // ImagessController $c
    ): Response {
        // $c->test();
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
        $search = $request->query->get('search1');
        $filter = null;
        $filter = $request->get('inputfilter');
        if ($filter) {
            $SousService = $SousserviceRepository->getAllByServiceName($filter);
        } else if ($search) {
            $SousService = $SousserviceRepository->findOneByName($search);
        } else {
            $SousService = $SousserviceRepository->findAll();
        }
        $listimg = [];
        foreach ($SousService as $serv) {
            $firstimg = $ImagessRepository->findBySousService($serv);
            if (!empty($firstimg)) {
                $listimg[]  = $firstimg[0];
               // $listimg[] = $fimg;
                
            }
        }
        
        $imagess = $ImagessRepository->findAll();
        foreach ($SousService as $s) {
            $checkboxes = explode(',', $s->getIdEventcateg());
            $list = [];
            foreach ($checkboxes as $c) {
                $list[] = $CategEventRepository->findOneByIdCateg($c)->getType();
            }
            $selectedCheckboxes = implode(',', $list);
            $s->setIdEventcateg($selectedCheckboxes);
        }
        $service = $ServRepository->findAll();
        $query = $SousService;
        $pagination = $paginator->paginate(
            $query,
            $request->query->getInt('page', 1),
            7 // limit per page
        );
        return $this->render('templates_back/sousservice/index.html.twig', [
            'sousservices' => $pagination,
            'imagess' => $imagess,
            'options' => $service,
            'personne' => $personne,
            'eventCat' => $CategEventRepository->findAll(),
            'last' => $last,
            'firstimg' =>  $listimg,
           // 'fimg'=>$fimg
        ]);
    }
//ajout d'un sousservice -----------------------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_sousservice_new', methods: ['GET', 'POST'])]
    public function new(Request $request, SessionInterface $session, ImagePersRepository $imagePersRepository, CategEventRepository $CategEventRepository, PersonneRepository $PersonneRepository, SousserviceRepository $SousserviceRepository, ImagessRepository $imagessRepository, ServiceRepository $ServiceRepository): Response
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
        //----------------------------------------------
        $ss = new Sousservice();
        $form = $this->createForm(SousserviceType::class, $ss);
        $form->handleRequest($request);
        $errorMessage = null;
        $errorMessage1 = null;

        //-----------------------------------------------
        if ($form->isSubmitted()) {
            $cat = $request->request->get('my-checkbox');
            $desc = $form->get('description')->getData();
            $imag = $form->get('imagess')->getData();
            //------------------------------------------
            if ($cat) {
                $formIsValid1 = true;
            } else {
                $errorMessage = "selectionner au moin une categorie ";
                $formIsValid1 = false;
            }
            //-----------------------------------------
            if ($desc) {
                $formIsValid = true;
            } else {
                $formIsValid = false;
            }
            //------------------------------------------
            if ($imag ) {
                $formIsValid2 = true;
            } else {
                $errorMessage1 = "selectionner au moin une image ";
                $formIsValid2 = false;
            }
            //-----------------------------------------
            if ($formIsValid && $formIsValid1 && $formIsValid2 && $form->isValid()) {
                //----------------------------------------
                $imageFile = $form->get('imagess')->getData();
                foreach ($imageFile as $imageFile) {
                    $imageFilename = md5(uniqid()) . '.' . $imageFile->guessExtension();
                    $imageFile->move(
                        $this->getParameter('images_directory'),
                        $imageFilename  //configured fel config service.yaml                  
                    );
                    $imageEntity = new Imagess();
                    $imageEntity->setImg($imageFilename);
                    $ss->addImagess($imageEntity);
                }
                // ----------------------------------------
                $catev = $request->request->get('my-checkbox');
                $selectedCheckboxes = implode(',', $catev);
                $ss->setIdEventcateg($selectedCheckboxes);
                $ss->setIdPers($personne);
                $ss->setNote(0);
                // $SousserviceRepository->save($ss, true);
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($ss);
                $entityManager->flush();
                return $this->redirectToRoute('app_sousservice_index', [
                    // 'idss' => $ss->getId(),
                    // 'personne' => $personne,
                    // 'last' => $last,
                ], Response::HTTP_SEE_OTHER);
            }
        }
        return $this->renderForm('templates_back/sousservice/new.html.twig', [
            'eventCat' => $CategEventRepository->findAll(),
            'selectedCategories' => "",
            'sousservice' => $ss,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
            'errorMessage' => $errorMessage,
            'errorMessage1' => $errorMessage1,
        ]);
    }
//detail du sous service -------------------------------------------------------------------------------------------------
    #[Route('/{id}', name: 'app_sousservice_show', methods: ['GET'])]
    public function show(CategEventRepository $CategEventRepository, Sousservice $sousservice, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/sousservice/show.html.twig', [
            'eventCat' => $CategEventRepository->findAll(),
            'sousservice' => $sousservice,
            'personne' => $personne,
            'last' => $last,
        ]);
    }
//modifier sous service -------------------------------------------------------------------------------------------------
    #[Route('/{id}/edit', name: 'app_sousservice_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, ImagessRepository $ImagessRepository, SessionInterface $session, ImagePersRepository $imagePersRepository, CategEventRepository $CategEventRepository, PersonneRepository $PersonneRepository, Sousservice $sousservice, SousserviceRepository $SousserviceRepository): Response
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $iml = $ImagessRepository->findBysousService($sousservice);
        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');

        $form = $this->createForm(SousserviceType_edit::class, $sousservice);
        $form->handleRequest($request);
        $errorMessage = null;
        $errorMessage1 = null;
        $checkboxes = explode(',', $sousservice->getIdEventcateg());
        $list = [];
        foreach ($checkboxes as $c) {
            $list[] = $CategEventRepository->findOneByIdCateg($c);
        }
        if ($form->isSubmitted()) {

            $cat = $request->request->get('my-checkbox');
            $desc = $form->get('description')->getData();
            $imag = $form->get('imagess')->getData();
            if ($cat) {
                $formIsValid = true;
            } else {
                $errorMessage = "il faut selectionner au moin une categorie ";
                $formIsValid = false;
            }
            if ($desc) {
                $formIsValid1 = true;
            } else {
                $errorMessage = "merci d'inserer une description ";
                $formIsValid1 = false;
            }
            $imageFile = $form->get('imagess')->getData();
            foreach ($imageFile as $imageFile) {

                $imageFilename = md5(uniqid()) . '.' . $imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('images_directory'),
                    $imageFilename  //configured fel config service.yaml                  
                );
                $imageEntity = new Imagess();
                $imageEntity->setImg($imageFilename);
                $sousservice->addImagess($imageEntity);
            }

            // ----------------------------------------
            if ($formIsValid && $formIsValid1  && $form->isValid()) {
                $catev = $request->get('my-checkbox');
                $selectedCheckboxes = implode(',', $catev);
                $sousservice->setIdEventcateg($selectedCheckboxes);
                $sousservice->setIdPers($personne);
                $sousservice->setNote(0);
                // $sousservice->setImagess($iml[0]->getImg());
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($sousservice);
                $entityManager->flush();
                return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
            }
        }
        return $this->renderForm('templates_back/sousservice/edit.html.twig', [
            'eventCat' => $CategEventRepository->findAll(),
            'selectedCategories' => $list,
            'sousservice' => $sousservice,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
            'imglist' => $iml,
            'errorMessage' => $errorMessage,
            'errorMessage1' => $errorMessage1,
        ]);
    }
//suppression sous service-------------------------------------------------------------------------------------------------
    #[Route('/{id}', name: 'app_sousservice_delete', methods: ['POST'])]
    public function delete(Request $request, Sousservice $sousservice, SousserviceRepository $SousserviceRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $sousservice->getId(), $request->request->get('_token'))) {
            $SousserviceRepository->remove($sousservice, true);
        }

        return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
    }
//affichage des sousservices par service  -----------------------------------------------------------------------------------------------------------------     
    #[Route('/getAllByServiceName/{nom}', name: 'app_sousservice_getAllByServiceName', methods: ['GET'])]
    public function getAllByServiceName(SousserviceRepository $SousserviceRepository, $nom, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/sousservice/index.html.twig', [
            'sousservices' => $SousserviceRepository->getAllByServiceName($nom),
            'options' => "",
            'options1' => "",
            'personne' => $personne,
            'last' => $last,
        ]);
    }

    //========================================================================================================================================================================
    //========================================================================================================================================================================
    //=========================================MOBILE===============================================================================================================================
    //========================================================================================================================================================================
    //========================================================================================================================================================================

    #[Route('/AllSSMobile/list', name: 'app_sousservice_indexM', methods: ['GET'])]
    public function indexMobile(
        SerializerInterface $serializer,
        SousserviceRepository $SousserviceRepository,
        ServiceRepository $ServRepository,
        CategEventRepository $CategEventRepository,
        Request $request,
        SessionInterface $session,
        ImagePersRepository $imagePersRepository,
        ImagessRepository $ImagessRepository,
        PaginatorInterface $paginator,
        // ImagessController $c
    ): Response {
        // $c->test(); 
        $SousService = $SousserviceRepository->findAll();
        $json = $serializer->serialize($SousService, 'json', ['groups' => "souservices"]);
        return new Response($json); 
    }

    #[Route('/newMobile', name: 'app_sousservice_newMobile', methods: ['GET', 'POST'])]
    public function newMobile(NormalizerInterface $Normalizer,Request $request, SessionInterface $session, ImagePersRepository $imagePersRepository, CategEventRepository $CategEventRepository, PersonneRepository $PersonneRepository, SousserviceRepository $SousserviceRepository, ImagessRepository $imagessRepository, ServiceRepository $ServiceRepository): Response
    {
       
       
        $em = $this->getDoctrine()->getManager();
        $ss = new Sousservice();
        $ss->setNom($request->get('nom'));
        $ss->setDescription($request->get('description'));
        $ss->setPrix($request->get('prix'));
        $ss->setNote($request->get('note'));
        $ss->setIdEventcateg($request->get('id_eventCateg'));
        $ss->setIdService($SousserviceRepository->findByIdService($request->get('id_service')));
        $ss->setIdPers($PersonneRepository->findByIdPers($request->get('id_pers')));
        $em->persist($ss);
        $em->flush();
       
        $jsonContent = $Normalizer->normalize($ss, 'json', ['groups' => 'souservices']);
        return new Response(json_encode($jsonContent));
    }
    //-----------------------------------------------------------------------------------------------------------------------
    #[Route('/editMobile/{id}', name: 'app_sousservice_editMobile', methods: ['GET', 'POST'])]
    public function editMobile(NormalizerInterface $Normalizer,Request $request,SessionInterface $session,Sousservice $s,ImagePersRepository $imagePersRepository, CategEventRepository $CategEventRepository, PersonneRepository $PersonneRepository, SousserviceRepository $SousserviceRepository, ImagessRepository $imagessRepository, ServiceRepository $ServiceRepository): Response
    {
       
        $em = $this->getDoctrine()->getManager();
        $ss = $em->getRepository(Service::class)->find($s);
        $ss->setNom($request->get('nom'));
        $ss->setDescription($request->get('description'));
        $ss->setPrix($request->get('prix'));
        $ss->setNote($request->get('note'));
        $ss->setIdEventcateg($request->get('id_eventCateg'));
        $ss->setIdService($SousserviceRepository->findByIdService($request->get('id_service')));
        $ss->setIdPers($PersonneRepository->findByIdPers($request->get('id_pers')));
        $em->flush();
       
        $jsonContent = $Normalizer->normalize($ss, 'json', ['groups' => 'souservices']);
        return new Response("sousservice updated successfully " . json_encode($jsonContent));
    }
     //------------------------------------------------------------------------------------------------------------------------------
     #[Route('/deleteMobile/{id}', name: 'app_sousservice_deleteMobile')]
     public function deleteMobile(NormalizerInterface $Normalizer,Request $request, Sousservice $s, ServiceRepository $serviceRepository): Response
     {
         $em = $this->getDoctrine()->getManager();
         $serv = $em->getRepository(Sousservice::class)->find($s);
         $em->remove($serv);
         $em->flush();
         $jsonContent = $Normalizer->normalize($serv, 'json',['groups' => 'souservices']);
         return new Response("deleted successfully " . json_encode($jsonContent));
      
     }
}