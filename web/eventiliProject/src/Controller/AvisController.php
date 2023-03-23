<?php

namespace App\Controller;

use App\Entity\Avis;
use App\Form\AvisType;
use App\Repository\AvisRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/avis')]
class AvisController extends AbstractController
{
    #[Route('/', name: 'app_avis_index', methods: ['GET'])]
    public function index(AvisRepository $avisRepository): Response
    {
        return $this->render('avis/index.html.twig', [
            'avis' => $avisRepository->findAll(),
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/new', name: 'app_avis_new', methods: ['GET', 'POST'])]
    public function new(Request $request, AvisRepository $avisRepository): Response
    {
        $avi = new Avis();
        $form = $this->createForm(AvisType::class, $avi);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $avisRepository->save($avi, true);

            return $this->redirectToRoute('app_avis_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('avis/new.html.twig', [
            'avi' => $avi,
            'form' => $form,
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idAv}', name: 'app_avis_show', methods: ['GET'])]
    public function show(Avis $avi): Response
    {
        return $this->render('avis/show.html.twig', [
            'avi' => $avi,
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idAv}/edit', name: 'app_avis_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Avis $avi, AvisRepository $avisRepository): Response
    {
        $form = $this->createForm(AvisType::class, $avi);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $avisRepository->save($avi, true);

            return $this->redirectToRoute('app_avis_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('avis/edit.html.twig', [
            'avi' => $avi,
            'form' => $form,
        ]);
    }
//------------------------------------------------------------------------------------------------
    #[Route('/{idAv}', name: 'app_avis_delete', methods: ['POST'])]
    public function delete(Request $request, Avis $avi, AvisRepository $avisRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$avi->getIdAv(), $request->request->get('_token'))) {
            $avisRepository->remove($avi, true);
        }

        return $this->redirectToRoute('app_avis_index', [], Response::HTTP_SEE_OTHER);
    }
//------------------------------------------------------------------------------------------------
#[Route('/findAvisById/{idAv}', name: 'app_avis_findAvisById', methods: ['GET'])]
public function findAvisById(AvisRepository $AvisRepository,$idAv): Response
{
    return $this->render('avis/index.html.twig', [
        'avis' => $AvisRepository->findby(array('idAv'=>$idAv)),
    ]);
} 
//------------------------------------------------------------------------------------------------
#[Route('/findByIdSousService/{idService}', name: 'app_avis_findByIdSousService', methods: ['GET'])]
public function findByIdSousService(AvisRepository $AvisRepository,$idService): Response
{
    return $this->render('avis/index.html.twig', [
        'avis' => $AvisRepository->findby(array('idService'=>$idService)),
    ]);
} 
//------------------------------------------------------------------------------------------------
#[Route('/findByIdP/{pers}', name: 'app_avis_findByIdP', methods: ['GET'])]
public function findByIdP(AvisRepository $AvisRepository,$pers): Response
{
    return $this->render('avis/index.html.twig', [
        'avis' => $AvisRepository->findby(array('pers'=>$pers)),
    ]);
}
//------------------------------------------------------------------------------------------------
#[Route('/getAvisByPersonName/{nom}', name: 'app_avis_getAvisByPersonName', methods: ['GET'])]
public function getAvisByPersonName(AvisRepository $AvisRepository,$nom): Response
{
    return $this->render('avis/index.html.twig', [
        'avis' => $AvisRepository->getAvisByPersonName($nom),
    ]);
}
}
