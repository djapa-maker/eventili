<?php

namespace App\Repository;

use App\Entity\CategEvent;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<CategEvent>
 *
 * @method CategEvent|null find($id, $lockMode = null, $lockVersion = null)
 * @method CategEvent|null findOneBy(array $criteria, array $orderBy = null)
 * @method CategEvent[]    findAll()
 * @method CategEvent[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CategEventRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, CategEvent::class);
    }

    public function save(CategEvent $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(CategEvent $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    //-------------------------------------------------------------------------

    /**
    * @return Sousservice[] Returns an array of Service objects
    */
    public function findListCateg(): array
    {
        $arrayNames=[];
        $arrayNames=$this->createQueryBuilder('s')
            
            ->getQuery()
            ->getResult()
        ;
        $tab2=[];
        foreach($arrayNames as $i)
        {
            $tab2[]=$i->getType();
        }
        return $tab2;
    }
}
