<?php

namespace App\Controller;

use App\Entity\Imgev;
use App\Form\ImgevType;
use App\Repository\ImgevRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/imgev')]
class ImgevController extends AbstractController
{
    #[Route('/', name: 'app_imgev_index', methods: ['GET'])]
    public function index(ImgevRepository $imgevRepository): Response
    {
        return $this->render('imgev/index.html.twig', [
            'imgevs' => $imgevRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_imgev_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ImgevRepository $imgevRepository): Response
    {
        $imgev = new Imgev();
        $form = $this->createForm(ImgevType::class, $imgev);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imgevRepository->save($imgev, true);

            return $this->redirectToRoute('app_imgev_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('imgev/new.html.twig', [
            'imgev' => $imgev,
            'form' => $form,
        ]);
    }

    #[Route('/{idImgev}', name: 'app_imgev_show', methods: ['GET'])]
    public function show(Imgev $imgev): Response
    {
        return $this->render('imgev/show.html.twig', [
            'imgev' => $imgev,
        ]);
    }

    #[Route('/{idImgev}/edit', name: 'app_imgev_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Imgev $imgev, ImgevRepository $imgevRepository): Response
    {
        $form = $this->createForm(ImgevType::class, $imgev);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imgevRepository->save($imgev, true);

            return $this->redirectToRoute('app_imgev_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('imgev/edit.html.twig', [
            'imgev' => $imgev,
            'form' => $form,
        ]);
    }

    #[Route('/{idImgev}', name: 'app_imgev_delete', methods: ['POST'])]
    public function delete(Request $request, Imgev $imgev, ImgevRepository $imgevRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$imgev->getIdImgev(), $request->request->get('_token'))) {
            $imgevRepository->remove($imgev, true);
        }

        return $this->redirectToRoute('app_imgev_index', [], Response::HTTP_SEE_OTHER);
    }
}
