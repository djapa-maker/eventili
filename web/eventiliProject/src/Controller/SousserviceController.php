<?php

namespace App\Controller;

use App\Entity\CategEvent;
use App\Entity\Sousservice;
use App\Entity\Service;
use App\Entity\Imagess;
use App\Entity\Personne;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\ImagePersRepository;
use App\Form\SousserviceType;
use App\Repository\SousserviceRepository;
use App\Repository\ServiceRepository;
use App\Repository\CategEventRepository;
use App\Repository\ImagessRepository;
use App\Repository\PersonneRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
#[Route('/sousservice')]
class SousserviceController extends AbstractController
{
    #[Route('/', name: 'app_sousservice_index', methods: ['GET'])]
    public function index(
        EntityManagerInterface $em,
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
        $filter = $request->query->get('inputfilter');

        if ($filter) {
            $SousService = $SousserviceRepository->getAllByServiceName($filter);
        } else if ($search) {
            $SousService = $SousserviceRepository->findOneByName($search);
        } else {
            $SousService = $SousserviceRepository->findAll();
        }
        $imagess=$ImagessRepository->findAll();
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
        2 // limit per page
    );
        return $this->render('templates_back/sousservice/index.html.twig', [
            'sousservices' => $pagination,
            'imagess'=>$imagess,
            'options' => $service,
            'personne' => $personne,
            'eventCat' => $CategEventRepository->findAll(),
            'last' => $last,
        ]);
    }

    //-------------------------------------------------------------------------------------------------
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
        $ss = new Sousservice();
        $form = $this->createForm(SousserviceType::class, $ss);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('image')->getData();
            if ($imageFile) {
                $imageFilename = uniqid() . '.' . $imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('images_directory'),
                    $imageFilename  //configured fel config service.yaml                  
                );
                $ss->setImage($imageFilename);
            }
            // ----------------------------------------
            $catev = $request->get('my-checkbox');
            $selectedCheckboxes = implode(',', $catev);
            $ss->setIdEventcateg($selectedCheckboxes);
            $ss->setIdPers($PersonneRepository->findOneByIdPers(18));
            $ss->setNote(0);
            $SousserviceRepository->save($ss, true);
            return $this->redirectToRoute('app_imagess_new', [
                'idss' => $ss->getId(),
                'personne' => $personne,
                'last' => $last,

            ], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('templates_back/sousservice/new.html.twig', [
            'eventCat' => $CategEventRepository->findAll(),
            'selectedCategories' => "",
            'sousservice' => $ss,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
        ]);
    }
    //-------------------------------------------------------------------------------------------------
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
    //-------------------------------------------------------------------------------------------------
    #[Route('/{id}/edit', name: 'app_sousservice_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, SessionInterface $session, ImagePersRepository $imagePersRepository, CategEventRepository $CategEventRepository, PersonneRepository $PersonneRepository, Sousservice $sousservice, SousserviceRepository $SousserviceRepository): Response
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
        $form = $this->createForm(SousserviceType::class, $sousservice);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('image')->getData();
            if ($imageFile) {
                $imageFilename = uniqid() . '.' . $imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('images_directory'),
                    $imageFilename  //configured fel config service.yaml                  
                );
                $sousservice->setImage($imageFilename);
            }
            // ----------------------------------------
            $catev = $request->get('my-checkbox');
            $selectedCheckboxes = implode(',', $catev);
            $sousservice->setIdEventcateg($selectedCheckboxes);
            $sousservice->setIdPers($PersonneRepository->findOneByIdPers(18));
            $sousservice->setNote(0);
            $SousserviceRepository->save($sousservice, true);
            return $this->redirectToRoute('app_imagess_edit', [
                'idss' => $sousservice->getId(),
                'personne' => $personne,
                'last' => $last,

            ], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('templates_back/sousservice/new.html.twig', [
            'eventCat' => $CategEventRepository->findAll(),
            'selectedCategories' => "",
            'sousservice' => $sousservice,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
        ]);
    }
    //-------------------------------------------------------------------------------------------------
    #[Route('/{id}', name: 'app_sousservice_delete', methods: ['POST'])]
    public function delete(Request $request, Sousservice $sousservice, SousserviceRepository $SousserviceRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $sousservice->getId(), $request->request->get('_token'))) {
            $SousserviceRepository->remove($sousservice, true);
        }

        return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
    }
    //-------------------------------------------------------------------------------------------------
    #[Route('/findSSById/{id}', name: 'app_sousservice_findSSById', methods: ['GET'])]
    public function findSSById(SessionInterface $session, ImagePersRepository $imagePersRepository, SousserviceRepository $SousserviceRepository, $id): Response
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
            'sousservices' => $SousserviceRepository->findby(array('id' => $id)),
            'personne' => $personne,
            'last' => $last,
        ]);
    }
    //-------------------------------------------------------------------------------------------------    
    #[Route('/findSSByName/{name}', name: 'app_sousservice_findSSByName', methods: ['GET'])]
    public function findSSByName(SousserviceRepository $SousserviceRepository, $name, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
            'sousservices' => $SousserviceRepository->findOneByName($name),
            'personne' => $personne,
            'last' => $last,
        ]);
    }
    //-------------------------------------------------------------------------------------------------    
    #[Route('/findListByNames/{name}', name: 'app_sousservice_findListByNames', methods: ['GET'])]
    public function findListByNames(SousserviceRepository $SousserviceRepository, $name, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
            'sousservices' => $SousserviceRepository->findListByNames($name),
            'personne' => $personne,
            'last' => $last,
        ]);
    }
    //-------------------------------------------------------------------------------------------------    
    #[Route('/findSSByServiceId/{idService}', name: 'app_sousservice_findSSByServiceId', methods: ['GET'])]
    public function findSSByServiceId(SousserviceRepository $SousserviceRepository, $idService, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
            'sousservices' => $SousserviceRepository->findby(array('idService' => $idService)),
            'personne' => $personne,
            'last' => $last,
        ]);
    }
    //-------------------------------------------------------------------------------------------------    
    #[Route('/findSSByServiceIdAndName/{idService}/{nom}', name: 'app_sousservice_findSSByServiceIdAndName', methods: ['GET'])]
    public function findSSByServiceIdAndName(SousserviceRepository $SousserviceRepository, $idService, $nom, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
            'sousservices' => $SousserviceRepository->findSSByServiceIdAndName($idService, $nom),
            'personne' => $personne,
            'last' => $last,
        ]);
    }
    //-------------------------------------------------------------------------------------------------    
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
}
