<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\ImgevRepository;


#[ORM\Entity(repositoryClass: ImgevRepository::class)]
class Imgev
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idImgev=null;

    #[ORM\Column]
    private ?String $imagee=null;


    #[ORM\ManyToOne(targetEntity: Evenement::class)]
    #[ORM\JoinColumn(name: "id_even", referencedColumnName: "id_ev")]
    private ?Evenement $idEven=null;
    

    public function getIdImgev(): ?int
    {
        return $this->idImgev;
    }

    public function getImagee(): ?string
    {
        return $this->imagee;
    }

    public function setImagee(string $imagee): self
    {
        $this->imagee = $imagee;

        return $this;
    }

    public function getIdEven(): ?Evenement
    {
        return $this->idEven;
    }

    public function setIdEven(?Evenement $idEven): self
    {
        $this->idEven = $idEven;

        return $this;
    }


}
