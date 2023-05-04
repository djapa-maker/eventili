<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;


use Symfony\Component\Validator\Constraints as Assert;


/**
 * Ticket
 *
 * @ORM\Table(name="ticket", indexes={@ORM\Index(name="fk_ev", columns={"idEvent"}), 
 * @ORM\Index(name="fk_tick", columns={"id_tran"})})
 * @ORM\Entity
 */

 

 #[ORM\Entity]
class Ticket
{
  
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'id_tick')]
    private ?int $idTick=null;

   

    #[ORM\Column]
    //#[Assert\NotBlank]
    private ?int $nbTick=null;

    #[ORM\Column]
   // #[Assert\NotBlank]
   // #[ORM\Range(min: 20, max: 100)]
    private ?Float $prixTick=null;

    #[ORM\Column]
    //#[Assert\NotBlank]
    private ?string $status=null;


   

  

  

    // #[ORM\ManyToOne(inversedBy:'Evenement')]
    #[ORM\ManyToOne(targetEntity: Evenement::class)]
    #[ORM\JoinColumn(name: "idEvent", referencedColumnName: "id_ev")]
 
    
    private ?Evenement $idevent=null;

   


    // #[ORM\ManyToOne(inversedBy:'Transaction')]
    #[ORM\ManyToOne(targetEntity: Transaction::class)]
    #[ORM\JoinColumn(name: "id_tran", referencedColumnName: "id_trans")]

    
    private ?Transaction $idTran=null;

    public function getIdTick(): ?int
    {
        return $this->idTick;
    }

    public function getNbTick(): ?int
    {
        return $this->nbTick;
    }

    public function setNbTick(int $nbTick): self
    {
        $this->nbTick = $nbTick;

        return $this;
    }

    public function getPrixTick(): ?float
    {
        return $this->prixTick;
    }

    public function setPrixTick(float $prixTick): self
    {
        $this->prixTick = $prixTick;

        return $this;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function setStatus(string $status): self
    {
        $this->status = $status;

        return $this;
    }

    public function getIdevent(): ?Evenement
    {
        return $this->idevent;
    }

    public function setIdevent(?Evenement $idevent): self
    {
        $this->idevent = $idevent;

        return $this;
    }

    public function getIdTran(): ?Transaction
    {
        return $this->idTran;
    }

    public function setIdTran(?Transaction $idTran): self
    {
        $this->idTran = $idTran;

        return $this;
    }

}