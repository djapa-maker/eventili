<?php

namespace App\Controller;
use App\Entity\Evenement;
use App\Entity\Ticket;
use App\Repository\EvenementRepository;
use App\Repository\ImagePersRepository;
use App\Repository\PersonneRepository;
use App\Repository\TransactionRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Endroid\QrCode\Color\Color;
use Endroid\QrCode\Encoding\Encoding;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelLow;
use Endroid\QrCode\QrCode;
use Endroid\QrCode\Label\Label;
use Doctrine\Persistence\ManagerRegistry;

use Symfony\Component\HttpFoundation\Request;

use Endroid\QrCode\Logo\Logo;
use Endroid\QrCode\Writer\PngWriter;
use Endroid\QrCode\Label\Font\NotoSans;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

class QrCodeController extends AbstractController
{
    #[Route('/qr-codes/{idEv}', name: 'app_qr_codes', methods: ['GET','POST'])]
public function index(int $idEv,UrlGeneratorInterface $urlGenerator,EntityManagerInterface $em, 
Request $request, ImagePersRepository $imagePersRepository, PersonneRepository $personneRepository, SessionInterface $session ): Response
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


    $writer = new PngWriter();
    
    // Get the event data from the database using the ID parameter
    $evenement = $em->getRepository(Evenement::class)->find($idEv);
    

    // Create the QR code with the event data
    $qrCode = QrCode::create(
        sprintf(
            "ID: %s\nPrix: %d\nTitre: %s\nDate de debut: %s\nDate de fin: %s\nDescription :%s\nRESERVATION_URL: %s",
            $evenement->getIdPers(),
            $evenement->getPrix(),
            $evenement->getTitre(),
            $evenement->getDateDebut()->format('Y-m-d H:i:s'),
            $evenement->getDateFin()->format('Y-m-d H:i:s'),
            $evenement->getDescriptionEv(),
            
            
           
            $reservationUrl = $urlGenerator->generate('app_home_ticket_acheter', [], UrlGeneratorInterface::ABSOLUTE_URL)
        )
    )->setEncoding(new Encoding('UTF-8'))
     ->setErrorCorrectionLevel(new ErrorCorrectionLevelLow())
     ->setSize(120)
     ->setMargin(0)
     ->setForegroundColor(new Color(0, 0, 0))
     ->setBackgroundColor(new Color(255, 255, 255));
    
    // Add logo and label to the QR code
    $logo = Logo::create('img.png')->setResizeToWidth(60);
    $label = Label::create('')->setFont(new NotoSans(8));
 
    // Generate QR codes with different styles
    $qrCodes = [];
    $qrCodes['img'] = $writer->write($qrCode, $logo)->getDataUri();
    $qrCodes['simple'] = $writer->write($qrCode, null, $label->setText('Simple'))->getDataUri();
    $qrCode->setForegroundColor(new Color(255, 0, 0));
    $qrCodes['changeColor'] = $writer->write($qrCode, null, $label->setText('Color Change'))->getDataUri();
    $qrCode->setForegroundColor(new Color(0, 0, 0))->setBackgroundColor(new Color(255, 0, 0));
    $qrCodes['changeBgColor'] = $writer->write($qrCode, null, $label->setText('Background Color Change'))->getDataUri();
    $qrCode->setSize(200)->setForegroundColor(new Color(0, 0, 0))->setBackgroundColor(new Color(255, 255, 255));
    $qrCodes['withImage'] = $writer->write($qrCode, $logo, $label->setText('With Image')->setFont(new NotoSans(20)))->getDataUri();
    //$reservationUrl = $urlGenerator->generate('app_location_new', []);
    return $this->render('qr_code/index.html.twig', array_merge($qrCodes,['reservationUrl' => null] , [
        'evenement' => $evenement,
        'personne' => $personne,
       
        'last' => $last,
]));
}



#[Route('/addaa', name: 'app_ticket_add', methods: ['GET', 'POST'])]
public function addaa(Request $request, EntityManagerInterface $entityManager ,
ImagePersRepository $imagePersRepository, EvenementRepository $EventRepository,TransactionRepository $TransactionRepository, SessionInterface $session): Response
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

    
    $ticket = new Ticket();
    $nb = $request->request->get('nbtick');
    $ticket->setNbTick($nb);
    $ticket->setPrixTick($request->request->get('prix')*$nb);
    $ticket->setStatus("active");
$idtr=10;
    $transaction=$TransactionRepository->findOneBy(['id_trans' => $idtr]);
    $ticket->setIdTran($transaction);
    $nom = $request->request->get('titre');
    $event=$EventRepository->findOneBy(['titre' => $nom]);
$ticket->setIdevent($event);
   $entityManager->persist($ticket);
        $entityManager->flush();

        return $this->redirectToRoute('app_home_ticket_front', 
        [
        'personne' => $personne,
            'last' => $last,], Response::HTTP_SEE_OTHER);
    

}

}