<?php

namespace App\Repository;
//---------------------------------------------------------------------------------------
use App\Entity\Imagess;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
//---------------------------------------------------------------------------------------
/**
 * @extends ServiceEntityRepository<Imagess>
 *
 * @method Imagess|null find($id, $lockMode = null, $lockVersion = null)
 * @method Imagess|null findOneBy(array $criteria, array $orderBy = null)
 * @method Imagess[]    findAll()
 * @method Imagess[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ImagessRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Imagess::class);
    }
//------------------------------------------------------------------------------------------------- 
    public function save(Imagess $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
//------------------------------------------------------------------------------------------------- 
    public function remove(Imagess $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
//---------------------------------------------------------------------------------------    
}
