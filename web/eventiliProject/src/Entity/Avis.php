<?php

namespace App\Entity;
//---------------------------------------------------------------------------------------
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\AvisRepository;
use Symfony\Component\Validator\Constraints as Assert;
//---------------------------------------------------------------------------------------
#[ORM\Entity(repositoryClass:AvisRepository::class)]
class Avis
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idAv=null;
//---------------------------------------------------------------------------------------
    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de donner une note')]
    #[Assert\Range(
        min: 0,
        max: 5,
        notInRangeMessage: 'il ne faut pas dépasser {{ max }} étoiles'
    )]
    private ?float $rating=null;
//---------------------------------------------------------------------------------------
    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le champ commentaire')]
    #[Assert\Length(
     // min: 1,
     max: 100,
     // minMessage: 'Your first name must be at least {{ limit }} characters long',
     maxMessage: 'notre commentaire ne doit pas dépasser {{ limit }} charactères',
 )]
    private ?string $comment= null;
//---------------------------------------------------------------------------------------
    #[ORM\Column(name: "Date", type: "datetime", nullable: false, options: ["default" => "CURRENT_TIMESTAMP"])]
    private  \DateTime $date;
//---------------------------------------------------------------------------------------
    // #[ORM\ManyToOne(inversedBy:'Avis')]
    #[ORM\ManyToOne(targetEntity: Personne::class)]
    #[ORM\JoinColumn(name: "pers", referencedColumnName: "id_pers")]
    private ?Personne $pers=null;
//---------------------------------------------------------------------------------------
    // #[ORM\ManyToOne(inversedBy:'Avis')]
    #[ORM\ManyToOne(targetEntity: Sousservice::class)]
    #[ORM\JoinColumn(name: "id_service", referencedColumnName: "id")]
    private ?Sousservice $idService=null;
//---------------------------------------------------------------------------------------
    public function getIdAv(): ?int
    {
        return $this->idAv;
    }
//---------------------------------------------------------------------------------------
    public function getRating(): ?float
    {
        return $this->rating;
    }
//---------------------------------------------------------------------------------------
    public function setRating(float $rating): self
    {
        $this->rating = $rating;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getComment(): ?string
    {
        return $this->comment;
    }
//---------------------------------------------------------------------------------------
    public function setComment(string $comment): self
    {
        $this->comment = $comment;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }
//---------------------------------------------------------------------------------------
    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getPers(): ?Personne
    {
        return $this->pers;
    }
//---------------------------------------------------------------------------------------
    public function setPers(?Personne $pers): self
    {
        $this->pers = $pers;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getIdService(): ?Sousservice
    {
        return $this->idService;
    }
//---------------------------------------------------------------------------------------
    public function setIdService(?Sousservice $idService): self
    {
        $this->idService = $idService;
        return $this;
    }
}
