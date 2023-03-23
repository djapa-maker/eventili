<?php

namespace App\Controller;

use App\Entity\Sousservice;
use App\Form\SousserviceType;
use App\Repository\SousserviceRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/sousservice')]
class SousserviceController extends AbstractController
{
    #[Route('/', name: 'app_sousservice_index', methods: ['GET'])]
    public function index(SousserviceRepository $SousserviceRepository): Response
    {
        return $this->render('sousservice/index.html.twig', [
            'sousservices' => $SousserviceRepository->findAll(),
        ]);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_sousservice_new', methods: ['GET', 'POST'])]
    public function new(Request $request, SousserviceRepository $SousserviceRepository): Response
    {
        $sousservice = new Sousservice();
        $form = $this->createForm(SousserviceType::class, $sousservice);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $SousserviceRepository->save($sousservice, true);

            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('sousservice/new.html.twig', [
            'sousservice' => $sousservice,
            'form' => $form,
        ]);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/{id}', name: 'app_sousservice_show', methods: ['GET'])]
    public function show(Sousservice $sousservice): Response
    {
        return $this->render('sousservice/show.html.twig', [
            'sousservice' => $sousservice,
        ]);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/{id}/edit', name: 'app_sousservice_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Sousservice $sousservice, SousserviceRepository $SousserviceRepository): Response
    {
        $form = $this->createForm(SousserviceType::class, $sousservice);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $SousserviceRepository->save($sousservice, true);

            return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('sousservice/edit.html.twig', [
            'sousservice' => $sousservice,
            'form' => $form,
        ]);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/{id}', name: 'app_sousservice_delete', methods: ['POST'])]
    public function delete(Request $request, Sousservice $sousservice, SousserviceRepository $SousserviceRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$sousservice->getId(), $request->request->get('_token'))) {
            $SousserviceRepository->remove($sousservice, true);
        }

        return $this->redirectToRoute('app_sousservice_index', [], Response::HTTP_SEE_OTHER);
    }
//-------------------------------------------------------------------------------------------------
    #[Route('/findSSById/{id}', name: 'app_sousservice_findSSById', methods: ['GET'])]
    public function findSSById(SousserviceRepository $SousserviceRepository,$id): Response
    {
        return $this->render('sousservice/index.html.twig', [
            'sousservices' => $SousserviceRepository->findby(array('id'=>$id)),
        ]);
    }
//-------------------------------------------------------------------------------------------------    
    #[Route('/findSSByName/{name}', name: 'app_sousservice_findSSByName', methods: ['GET'])]
    public function findSSByName(SousserviceRepository $SousserviceRepository,$name): Response
    {
        return $this->render('sousservice/index.html.twig', [
            'sousservices' => $SousserviceRepository->findOneByName($name),
        ]);
    }
//-------------------------------------------------------------------------------------------------    
#[Route('/findListByNames/{name}', name: 'app_sousservice_findListByNames', methods: ['GET'])]
public function findListByNames(SousserviceRepository $SousserviceRepository,$name): Response
{
    return $this->render('sousservice/index.html.twig', [
        'sousservices' => $SousserviceRepository->findListByNames($name),
    ]);
}
//-------------------------------------------------------------------------------------------------    
#[Route('/findSSByServiceId/{idService}', name: 'app_sousservice_findSSByServiceId', methods: ['GET'])]
public function findSSByServiceId(SousserviceRepository $SousserviceRepository,$idService): Response
{
    return $this->render('sousservice/index.html.twig', [
        'sousservices' => $SousserviceRepository->findby(array('idService'=>$idService)),
    ]);
}
//-------------------------------------------------------------------------------------------------    
#[Route('/findSSByServiceIdAndName/{idService}/{nom}', name: 'app_sousservice_findSSByServiceIdAndName', methods: ['GET'])]
public function findSSByServiceIdAndName(SousserviceRepository $SousserviceRepository,$idService,$nom): Response
{
    return $this->render('sousservice/index.html.twig', [
        'sousservices' => $SousserviceRepository->findSSByServiceIdAndName($idService,$nom),
    ]);
}
//-------------------------------------------------------------------------------------------------    
#[Route('/getAllByServiceName/{nom}', name: 'app_sousservice_getAllByServiceName', methods: ['GET'])]
public function getAllByServiceName(SousserviceRepository $SousserviceRepository,$nom): Response
{
    return $this->render('sousservice/index.html.twig', [
        'sousservices' => $SousserviceRepository->getAllByServiceName($nom),
    ]);
}
}
