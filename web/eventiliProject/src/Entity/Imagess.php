<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\ImagessRepository;

#[ORM\Entity(repositoryClass: ImagessRepository::class)]
class Imagess
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idimgss=null;

    #[ORM\Column]
    private ?String $img=null;

    // #[ORM\ManyToOne(inversedBy:'Imagess')]
    #[ORM\ManyToOne(targetEntity: Sousservice::class)]
    #[ORM\JoinColumn(name: "sous_service", referencedColumnName: "id")]
    private ?Sousservice $sousService=null;
//---------------------------------------------------------------------------------------
    public function getIdimgss(): ?int
    {
        return $this->idimgss;
    }

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(string $img): self
    {
        $this->img = $img;
        return $this;
    }

    public function getSousService(): ?Sousservice
    {
        return $this->sousService;
    }

    public function setSousService(?Sousservice $sousService): self
    {
        $this->sousService = $sousService;
        return $this;
    }

}
