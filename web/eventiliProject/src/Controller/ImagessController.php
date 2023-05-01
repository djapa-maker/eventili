<?php

namespace App\Controller;
use App\Entity\Imagess;
use App\Entity\Sousservice;
use App\Form\ImagessType;
use App\Repository\ImagessRepository;
use App\Repository\SousserviceRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use App\Repository\ImagePersRepository;
#[Route('/imagess')]
class ImagessController extends AbstractController
{
//affichage ---------------------------------------------------------------------------------------    
    #[Route('/', name: 'app_imagess_index', methods: ['GET'])]
    public function index(ImagessRepository $imagessRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
        return $this->render('templates_back/imagess/index.html.twig', [
            'imagess' => $imagessRepository->findAll(),
            'personne' => $personne,
            'last' => $last,
        ]);
    }
//ajout ------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_imagess_new', methods: ['GET', 'POST'])]
    public function new(Request $request, SousserviceRepository $SousserviceRepository, ImagessRepository $imagessRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
        $imagess = new Imagess();
        $ss = $request->query->get('idss'); 
        $form = $this->createForm(ImagessType::class, $imagess);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            $imagess->setSousService($SousserviceRepository->findOneById($ss));
            $imagessRepository->save($imagess, true);
            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        } 
        else if ($form->isSubmitted()) {
            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('templates_back/imagess/new.html.twig', [
            'imagess' => $imagess,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
        ]);
    }
// la creation d'une nouvelle image fel edit du sous service ------------------------------------------------------------------------------------------------
    #[Route('{id}/new1', name: 'app_imagess_new1', methods: ['GET', 'POST'])]
    public function new1(Request $request, Sousservice $Sousservice,SousserviceRepository $SousserviceRepository, ImagessRepository $imagessRepository, SessionInterface $session, ImagePersRepository $imagePersRepository): Response
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
        //------------------------------------
        $imagess = new Imagess();
        $i = new Imagess();
        $form = $this->createForm(ImagessType::class, $imagess);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('img')->getData();
            if ($imageFile) {
                $imageFilename = uniqid() . '.' . $imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('images_directory'),
                    $imageFilename  //configured fel config service.yaml                  
                );
                $imagess->setImg($imageFilename);
            }
            $imagess->setSousService($SousserviceRepository->findOneById($Sousservice));
            $imagessRepository->save($imagess, true);
            $i->setImg($SousserviceRepository->findOneById($Sousservice)->getImagess());
            $i->setSousService($SousserviceRepository->findOneById($Sousservice));
            $imagessRepository->save($i, true);
            return $this->redirectToRoute('app_sousservice_edit', [
                'id'=> $imagess->getSousService()->getId(),
            ], Response::HTTP_SEE_OTHER);
        }
        else if ($form->isSubmitted()) {
            $i->setImg($SousserviceRepository->findOneById($Sousservice)->getImagess());
            $i->setSousService($SousserviceRepository->findOneById($Sousservice));
            $imagessRepository->save($i, true);
            return $this->redirectToRoute('app_sousservice_edit', [
                'id'=> $imagess->getSousService()->getId(),
            ], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('templates_back/imagess/new.html.twig', [
            'imagess' => $imagess,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
        ]);
    }
// update de l'image ss ------------------------------------------------------------------------------------------------
    #[Route('/{idimgss}/edit', name: 'app_imagess_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, SousserviceRepository $SousserviceRepository, Imagess $imagess, SessionInterface $session, ImagePersRepository $imagePersRepository, ImagessRepository $imagessRepository): Response
    {
        $personne = $session->get('id');
        $idPerss = $session->get('personne');
        $images = $imagePersRepository->findBy(['idPers' => $idPerss]);
        $images = array_reverse($images);
        $errorMessage =null;
        if (!empty($images)) {
            $i = $images[0];
            $last = $i->getLast();
        } else {
            $last = "account (1).png";
        }
        $session->set('last', $last);
        $last = $session->get('last');
        $form = $this->createForm(ImagessType::class, $imagess);
        $form->handleRequest($request);

        $imag = $form->get('img')->getData();
        if ($imag) {
            $formIsValid = true;
        } else {
            $errorMessage = "Il faut selectionner une image ";
            $formIsValid = false;
        }
        if ($form->isSubmitted() && $formIsValid && $form->isValid()) {
            $imageFile = $form->get('img')->getData(); 
            if ($imageFile) {
                $imageFilename = uniqid() . '.' . $imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('images_directory'),
                    $imageFilename  //configured fel config service.yaml                  
                );
                $imagess->setImg($imageFilename);
            }
            $imagessRepository->save($imagess, true);
            return $this->redirectToRoute('app_sousservice_edit', ['id' => $imagess->getSousService()->getId() ], Response::HTTP_SEE_OTHER);
        } 
        return $this->renderForm('templates_back/imagess/edit.html.twig', [
            'imagess' => $imagess,
            'form' => $form,
            'personne' => $personne,
            'last' => $last,
            'errorMessage'=>$errorMessage 
        ]);
    }
// suppression de l'image ss ------------------------------------------------------------------------------------------------
    #[Route('/{idimgss}', name: 'app_imagess_delete', methods: ['POST'])]
    public function delete(Request $request, Imagess $imagess, ImagessRepository $imagessRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $imagess->getIdimgss(), $request->request->get('_token'))) {
            $imagessRepository->remove($imagess, true);
        }
        return $this->redirectToRoute('app_sousservice_edit', [
            'id'=>$imagess->getSousService()->getId()
        ], Response::HTTP_SEE_OTHER);
    }
}
