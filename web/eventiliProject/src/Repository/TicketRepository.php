<?php

namespace App\Repository;

use App\Entity\Ticket;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Ticket>
 *
 * @method Ticket|null find($id, $lockMode = null, $lockVersion = null)
 * @method Ticket|null findOneBy(array $criteria, array $orderBy = null)
 * @method Ticket[]    findAll()
 * @method Ticket[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class TicketRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Ticket::class);
    }

    public function save(Ticket $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Ticket $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }


    public function findByStatus($status)
    {
    return $this->createQueryBuilder('t')
    ->where('t.status LIKE :status')
    ->setParameter('status', '%'.$status.'%')
    ->getQuery()
    ->getResult();
    }


    public function findByNbTick($nbTick)
    {
    return $this->createQueryBuilder('t')
    ->where('t.nbTick LIKE :nbtick')
    ->setParameter('nbtick', '%'.$nbTick.'%')
    ->getQuery()
    ->getResult();
    }

    public function findByPrixTick($prixTick)
    {
    return $this->createQueryBuilder('t')
    ->where('t.prixTick LIKE :prixTick')
    ->setParameter('prixTick', '%'.$prixTick.'%')
    ->getQuery()
    ->getResult();
    }


    public function findByStatuss($status){

        $em = $this->getEntityManager();

        $query = $em->createQuery(
            'SELECT DISTINCT  count(t.idTick) FROM  App\Entity\Ticket t  where t.status = :status  '
        );
        $query->setParameter('status', $status);
        return $query->getResult();
    }

/*
    public function findOneBystat($value): array
    {
        return $this->createQueryBuilder('t')
            ->where('t.status like :s')
            ->setParameter('s', '%'.$value.'%')
            ->getQuery()
            ->getResult()
        ;
    }
//-----------------------------------------------------------------------------
   /**
    * @return Service Returns  Ticket objects
   
    public function findOneBystats($value): array
    {
        return $this->createQueryBuilder('t')
            ->where('t.status like :s')
            ->setParameter('s', '%'.$value.'%')
            ->getQuery()
            ->getResult()
        ;
    }*/
//---------------------------------------------------------------------------------------    





//    /**
//     * @return Ticket[] Returns an array of Ticket objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('e.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Ticket
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
