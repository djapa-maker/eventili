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

#[Route('/imagess')]
class ImagessController extends AbstractController
{
    #[Route('/', name: 'app_imagess_index', methods: ['GET'])]
    public function index(ImagessRepository $imagessRepository): Response
    {
        return $this->render('imagess/index.html.twig', [
            'imagess' => $imagessRepository->findAll(),
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_imagess_new', methods: ['GET', 'POST'])]
    public function new(Request $request,SousserviceRepository $SousserviceRepository, ImagessRepository $imagessRepository ): Response
    {
        $imagess = new Imagess();
        $i=new Imagess(); 
        $ss=$request->query->get('idss'); //get id of sous service
        //create the form
        $form = $this->createForm(ImagessType::class, $imagess);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            //image upload
            $imageFiles = $form->get('img')->getData();
            $tabImg=[];
            foreach($imageFiles as $imageFile)
            {
            if ($imageFile) {
                $imageFilename = uniqid() . '.' . $imageFile->guessExtension();
                $imageFile->move(
                    $this->getParameter('images_directory'),
                    $imageFilename  //configured fel config service.yaml                  
                );
                $tabImg[]=$imageFilename;
                foreach($tabImg as $t){
                    $imagess->setImg($t);
                }
                

            }
            $imagess->setSousService($SousserviceRepository->findOneById($ss));
            $imagessRepository->save($imagess, true);
            }
            //setting image with sous service image and new images
            $i->setImg($SousserviceRepository->findOneById($ss)->getImage());
            $i->setSousService($SousserviceRepository->findOneById($ss));
            $imagessRepository->save($i, true);
        
            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        }
        //in case there's no image added in the extra image page
        //setting image only with sous service image  
        else if($form->isSubmitted()){
            $i->setImg($SousserviceRepository->findOneById($ss)->getImage());
            $i->setSousService($SousserviceRepository->findOneById($ss));
            $imagessRepository->save($i, true);
            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        }
        //calling imagess creation page
        return $this->renderForm('imagess/new.html.twig', [
            'imagess' => $imagess,
            'form' => $form,
        ]);
    }

    #[Route('/{idimgss}', name: 'app_imagess_show', methods: ['GET'])]
    public function show(Imagess $imagess): Response
    {
        return $this->render('imagess/show.html.twig', [
            'imagess' => $imagess,
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idimgss}/edit', name: 'app_imagess_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request,SousserviceRepository $SousserviceRepository, Imagess $imagess, ImagessRepository $imagessRepository): Response
    { 
        $i=new Imagess();
        $ss=$request->query->get('idss');

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
            $i->setImg($SousserviceRepository->findOneById($ss)->getImage());
            $i->setSousService($SousserviceRepository->findOneById($ss));
            $imagessRepository->save($i, true);
            
            $imagess->setSousService($SousserviceRepository->findOneById($ss));
            $imagessRepository->save($imagess, true);

            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        }
        else if($form->isSubmitted()){
            $i->setImg($SousserviceRepository->findOneById($ss)->getImage());
            $i->setSousService($SousserviceRepository->findOneById($ss));
            $imagessRepository->save($i, true);
            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        }

//------------------------------------------------------------------------------------------------
        return $this->renderForm('imagess/new.html.twig', [
            'imagess' => $imagess,
            'form' => $form,
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idimgss}', name: 'app_imagess_delete', methods: ['POST'])]
    public function delete(Request $request, Imagess $imagess, ImagessRepository $imagessRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$imagess->getIdimgss(), $request->request->get('_token'))) {
            $imagessRepository->remove($imagess, true);
        }

        return $this->redirectToRoute('app_imagess_index', [], Response::HTTP_SEE_OTHER);
    }
//---------------------------------------------------------------------------------------------------
#[Route('/findImagessById/{idimgss}', name: 'app_imagess_findImagessById', methods: ['GET'])]
public function findImagessById(ImagessRepository $ImagessRepository,$idimgss): Response
{
    return $this->render('imagess/index.html.twig', [
        'imagess' =>$ImagessRepository->findby(array('idimgss'=>$idimgss)),
    ]);
}
//---------------------------------------------------------------------------------------------------
    #[Route('/findImageByIdSS/{sousService}', name: 'app_imagess_findImageByIdSS', methods: ['GET'])]
    public function findImageByIdSS(ImagessRepository $ImagessRepository,$sousService): Response
    {
        return $this->render('imagess/index.html.twig', [
            'imagess' =>$ImagessRepository->findby(array('sousService'=>$sousService)),
        ]);
    }
}
