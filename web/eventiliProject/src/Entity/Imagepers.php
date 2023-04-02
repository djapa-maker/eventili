<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\ImagePersRepository;


#[ORM\Entity(repositoryClass: ImagepersRepository::class)]

class Imagepers
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idImp=null;
    
    
    #[ORM\Column(length:255)]
    private ?String $imagep=null;
   
    #[ORM\Column(length:255)]
    private ?String $last=null;
   
    #[ORM\ManyToOne(targetEntity: Personne::class)]
    #[ORM\JoinColumn(name: "id_pers", referencedColumnName: "id_pers")]
    private ?Personne $idPers=null;
    
    /*#[ORM\ManyToOne(inversedBy: 'Personne')]
    private ?Personne $idPers=null;*/

    public function getIdImp(): ?int
    {
        return $this->idImp;
    }

    public function getImagep(): ?string
    {
        return $this->imagep;
    }

    public function setImagep(string $imagep): self
    {
        $this->imagep = $imagep;

        return $this;
    }

    public function getLast(): ?string
    {
        return $this->last;
    }

    public function setLast(string $last): self
    {
        $this->last = $last;

        return $this;
    }

    public function getIdPers(): ?Personne
    {
        return $this->idPers;
    }

    public function setIdPers(?Personne $idPers): self
    {
        $this->idPers = $idPers;

        return $this;
    }


}
