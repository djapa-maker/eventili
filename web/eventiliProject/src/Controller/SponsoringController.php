<?php

namespace App\Controller;

use App\Entity\Sponsoring;
use App\Form\SponsoringType;
use App\Repository\SponsoringRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/sponsoring')]
class SponsoringController extends AbstractController
{
    #[Route('/', name: 'app_sponsoring_index', methods: ['GET'])]
    public function index(SponsoringRepository $sponsoringRepository): Response
    {
        return $this->render('sponsoring/index.html.twig', [
            'sponsorings' => $sponsoringRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_sponsoring_new', methods: ['GET', 'POST'])]
    public function new(Request $request, SponsoringRepository $sponsoringRepository): Response
    {
        $sponsoring = new Sponsoring();
        $form = $this->createForm(SponsoringType::class, $sponsoring);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $sponsoringRepository->save($sponsoring, true);

            return $this->redirectToRoute('app_sponsoring_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('sponsoring/new.html.twig', [
            'sponsoring' => $sponsoring,
            'form' => $form,
        ]);
    }

    #[Route('/{id_sponso}', name: 'app_sponsoring_show', methods: ['GET'])]
    public function show(Sponsoring $sponsoring): Response
    {
        return $this->render('sponsoring/show.html.twig', [
            'sponsoring' => $sponsoring,
        ]);
    }

    #[Route('/{id_sponso}/edit', name: 'app_sponsoring_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Sponsoring $sponsoring, SponsoringRepository $sponsoringRepository): Response
    {
        $form = $this->createForm(SponsoringType::class, $sponsoring);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $sponsoringRepository->save($sponsoring, true);

            return $this->redirectToRoute('app_sponsoring_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('sponsoring/edit.html.twig', [
            'sponsoring' => $sponsoring,
            'form' => $form,
        ]);
    }

    #[Route('/{id_sponso}', name: 'app_sponsoring_delete', methods: ['POST'])]
    public function delete(Request $request, Sponsoring $sponsoring, SponsoringRepository $sponsoringRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$sponsoring->getIdSponso(), $request->request->get('_token'))) {
            $sponsoringRepository->remove($sponsoring, true);
        }

        return $this->redirectToRoute('app_sponsoring_index', [], Response::HTTP_SEE_OTHER);
    }
}
