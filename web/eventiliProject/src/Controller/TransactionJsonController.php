<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Personne;
use App\Entity\Transaction;
use App\Repository\TransactionRepository;
use DateTimeZone;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\SerializerInterface;
class TransactionJsonController extends AbstractController
{
    #[Route('/transaction/json', name: 'app_transaction_json')]
    public function index(): Response
    {
        return $this->render('transaction_json/index.html.twig', [
            'controller_name' => 'TransactionJsonController',
        ]);
    }

//---------------------------
    #[Route('/transactionsearchiducrit', name: 'app_find_transaction_by_iduser')]
    public function findByUserIdAndValeurTransGreaterThan(Request $request, EntityManagerInterface $entityManager)
    {
        $valeurTrans = $request->query->get('valmin');
        $userId = $request->query->get('userId');
        $transactions  = $entityManager->createQueryBuilder ()
            ->select('t')
            ->from('App\Entity\Transaction', 't')
            ->where('t.user_id = :userId')
            ->andWhere('t.valeur_trans > :valeurTrans')
            ->setParameters(array('userId' => $userId, 'valeurTrans' => $valeurTrans))
            ->getQuery()
            ->getResult();;

        if (!$transactions) {
            throw $this->createNotFoundException('no Transactions with valu min  ' . $valeurTrans);
        }

        return $this->json($transactions);
    }
//-------------------------------------------
    #[Route('/transactionsearchidu', name: 'app_find_transaction_by_iduserza')]
    public function findTransactionByUserId(Request $request, TransactionRepository $repo)
    {
        $iduser = $request->query->get('iduser');

        $transactions = $repo->findBy(['user_id' => $iduser]);

        if (!$transactions) {
            throw $this->createNotFoundException('No transactions found for this user.');
        }

        return $this->json($transactions);
    }
//-------------------------------
    #[Route('/transactionsbyuser', name: 'app_find_transactions_by_user')]
    public function findTransactionsByUser(Request $request, EntityManagerInterface $entityManager)
    {
        $nomPers = $request->query->get('nomPers');

        $personne = $entityManager->getRepository(Personne::class)->findOneBy(['nomPers' => $nomPers]);

        if (!$personne) {
            throw $this->createNotFoundException('Personne not found with nomPers ' . $nomPers);
        }

        $transactions = $entityManager->createQueryBuilder()
            ->select('t')
            ->from('App\Entity\Transaction', 't')
            ->where('t.user_id = :userId')
            ->setParameter('userId', $personne->getIdPers())
            ->getQuery()
            ->getResult();

        if (!$transactions) {
            throw $this->createNotFoundException('Transactions not found for user with nomPers ' . $nomPers);
        }

        return $this->json($transactions);
    }
//----------------------
    #[Route('/transactionsearh', name: 'app_find_transaction')]
    public function findTransactionById(Request $request,TransactionRepository $repo, EntityManagerInterface $entityManager)
    {

        $id = $request->query->get('idtrans');

        $transaction = $entityManager->getRepository(Transaction::class)->find($id);

        if (!$transaction) {
            throw $this->createNotFoundException('Transaction not found.');
        }

        return $this->json($transaction);
    }
//-----------------------------
    #[Route('/savetransaction', name: 'app_save_transaction')]
    public function saveTransaction(Request $request, EntityManagerInterface $entityManager)
    {
        $user = $entityManager->find(Personne::class, $request->query->get('idpers'));
        //trans zentity
        $transaction = new Transaction();
        $transaction->setValeurTrans( $request->query->get('valeur_trans'));
        $transaction->setDevis( $request->query->get('devis'));
        $transaction->setDateTrans(new \DateTime('now', new DateTimeZone('America/New_York')));//date now
        $transaction->setModeTrans( $request->query->get('mode_trans'));
        $transaction->setMontantTot( $request->query->get('montant_tot'));
        $transaction->setUserId($user);//findd by id
        //

        $entityManager->persist($transaction);
        $entityManager->flush();

        return new Response("Transaction saved with id " . $transaction->getId_trans(), Response::HTTP_CREATED);
    }//-----------------------------
//-------------------------------------------------get all
    #[Route('/getalltransactions', name: 'app_code_json')]
    public function coode(TransactionRepository $repo, Request $req, SerializerInterface $serializer)
    {
        $id = $req->query->get('idperso');
        $em = $this->getDoctrine()->getManager();
        $transaction = $em->getRepository(Transaction::class)->findOneBy(['user_id' => $id]);

        if ($transaction != null) {
            $json = $serializer->serialize($transaction, 'json');

            //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
            return new Response($json);
        } else {
            return new Response("Erreur : aucun transaction trouvé !");
        }

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON

    }
}
