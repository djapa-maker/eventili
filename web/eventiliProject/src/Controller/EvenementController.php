<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Imgev;
use App\Form\EvenementType;
use App\Repository\CategEventRepository;
use App\Repository\EvenementRepository;
use App\Repository\ImgevRepository;
use App\Repository\PersonneRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\ImagePersRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;

#[Route('/evenement')]
class EvenementController extends AbstractController
{


    #[Route('/', name: 'app_evenement_index', methods: ['GET'])]
    public function index(ImagePersRepository $imagePersRepository, EvenementRepository $evenementRepository, SessionInterface $session, CategEventRepository $categEventRepository, ImgevRepository $imgevRepository): Response
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


        $Categ = $categEventRepository->findAll();
        $evenements = $evenementRepository->findBy(['idPers' => $idPerss]);
        $imgev = [];

        foreach ($evenements as $event) {
            $imgev[$event->getIdEv()] = $imgevRepository->findBy(['idEven' => $event->getIdEV()]);
        }
        return $this->render('templates_front/evenement/index.html.twig', [
            'evenements' => $evenements,
            'Categ' => $Categ,
            'Img' => $imgev,
            'personne' => $personne,
        ]);
    }

    #[Route('/new', name: 'app_evenement_new', methods: ['GET', 'POST'])]
    public function new(SessionInterface $session, ImagePersRepository $imagePersRepository, Request $request, EvenementRepository $evenementRepository, PersonneRepository $PersonneRepository): Response
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


        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);
        $errorMessage = null;
        $errorMessage2 = null;
        $errorMessage3 = null;
        $errorMessage4 = null;
        $errorMessage5 = null;
        $allImagesValid = true;

        if ($form->isSubmitted()) {
            $date = $request->get('event_date');
            $timeString = $request->get('event_time');
            list($startTime, $endTime) = array_map('trim', explode('-', $timeString));
            $startTimeStamp = strtotime($startTime);
            $endTimeStamp = strtotime($endTime);
            $images = $form->get('imgev')->getData();

            if ($date === "" || $startTimeStamp > $endTimeStamp || empty($images) || $images[0]->getError() == UPLOAD_ERR_NO_FILE) {
                // If any of the conditions is true, set the appropriate error message and set $formIsValid to false
                if ($date === "") {
                    $errorMessage = "La date est obligatoire.";
                }
                if ($startTimeStamp > $endTimeStamp) {
                    $errorMessage3 = "Heure debut doit etre inférieure à l'heure fin.";
                }
                if (empty($images) || $images[0]->getError() == UPLOAD_ERR_NO_FILE) {
                    $errorMessage4 = "Il faut choisir au moin une image";
                }
                $formIsValid = false;
            } else {
                // If all conditions are false, move the uploaded image files and set $formIsValid to true
                foreach ($images as $image) {
                    if (!in_array($image->getMimeType(), ['image/jpeg', 'image/png', 'image/gif'])) {
                        $errorMessage5 = "Merci d'entrer des images valides";
                        $formIsValid = false;
                        $allImagesValid = false;
                    } else {
                        $fichier = md5(uniqid()) . '.' . $image->guessExtension();
                        $image->move(
                            $this->getParameter('images_directory'),
                            $fichier
                        );
                        $img = new Imgev();
                        $img->setImagee($fichier);
                        $evenement->addImage($img);
                        if ($allImagesValid) {
                            $formIsValid = $form->isValid();
                        }
                    }
                }
            }

            if ($formIsValid) {
                $dateDebString = $date . ' ' . $startTime;
                $dateFinString = $date . ' ' . $endTime;
                $dateDeb = new \DateTime($dateDebString);
                $dateFin = new \DateTime($dateFinString);

                $evenement->setDateDebut($dateDeb);
                $evenement->setDateFin($dateFin);
                $evenement->setVisibilite('Privé');

                $prix = $form->get('prix')->getData();
                $nbticket = $form->get('limiteparticipant')->getData();

                // var_dump($prix);
                // die();
                $evenement->setPrix($prix);
                $evenement->setLimiteparticipant($nbticket);
                if ($prix != "0") {
                    $evenement->setType('Payant');
                } else {
                    $evenement->setType('Gratuit');
                    // $evenement->setPrix(0);
                    $evenement->setLimiteparticipant(0);
                }

                $evenement->setIdPers($PersonneRepository->findOneByIdPers($personne));
                //$evenementRepository->save($evenement, true);
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($evenement);
                $entityManager->flush();

                return $this->redirectToRoute('app_reservation_index');
            }
        }
        return $this->renderForm('templates_front/evenement/new.html.twig', [
            'evenements' => $evenement,
            'form' => $form,
            'errorMessage' => $errorMessage,
            'errorMessage2' => $errorMessage2,
            'errorMessage3' => $errorMessage3,
            'errorMessage4' => $errorMessage4,
            'errorMessage5' => $errorMessage5,
            'personne' => $personne,
        ]);
    }


    // #[Route('/{idEv}', name: 'app_evenement_delete')]
    // public function delete( EvenementRepository $evenementRepository, $idEv ): Response
    // {
    //     $evenement = $evenementRepository->findOneByIdEv($idEv);
    //     $evenementRepository->remove($evenement);
    //     return $this->redirectToRoute('app_evenement_index');
    // }

    #[Route('/{idEv}', name: 'app_evenement_delete', methods: ['POST'])]
    public function delete(FlashyNotifier $flashy, Request $request, Evenement $evenement, EvenementRepository $evenementRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $evenement->getIdEv(), $request->request->get('_token'))) {
            $evenementRepository->remove($evenement, true);
            $titre = $evenement->getTitre();
            $flashy->success("$titre a été supprimé !");
        }

        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }



    // #[Route('/{idEv}', name: 'app_evenement_show', methods: ['GET'])]
    // public function show(Evenement $evenement): Response
    // {
    //     return $this->render('evenement/show.html.twig', [
    //         'evenement' => $evenement,
    //     ]);
    // }

    #[Route('/{idEv}/edit', name: 'app_evenement_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Evenement $evenement, EvenementRepository $evenementRepository): Response
    {
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $evenementRepository->save($evenement, true);

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('evenement/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }
}
