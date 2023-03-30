<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\SousserviceRepository;
use Symfony\Component\Validator\Constraints as Assert;
#[ORM\Entity(repositoryClass: SousserviceRepository::class)]
class Sousservice
{
    
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id=null;

   #[ORM\Column]
   #[Assert\NotBlank(message: 'Merci de remplir le nom')]
    private ?String $nom=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de choisir une image')]
    private ?String $image=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir la descritpion')]
    private ?String $description=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le prix')]
    // #[Assert\Regex(pattern: '/^[0-9]+(?:\.[0-9]+)?$/', message:"Le prix doit contenir uniquement des chiffres.")]
    private ?float $prix=null;

    #[ORM\Column]
    // #[Assert\Regex(pattern: '/^[0-9]*$')]
    private ?float $note=null;

    // #[ORM\ManyToOne(inversedBy:'Sousservice')]
    // #[ORM\ManyToOne(targetEntity: CategEvent::class)]
    // #[ORM\JoinColumn(name: "id_eventCateg", referencedColumnName: "id_categ")]
    #[ORM\Column]
    // #[Assert\NotBlank(message: 'Merci de selectionner un ou plusieurs evenement(s)')]
    private ?String $idEventcateg=null;

    // #[ORM\ManyToOne(inversedBy:'Sousservice')]
    #[ORM\ManyToOne(targetEntity: Personne::class)]
    #[ORM\JoinColumn(name: "id_pers", referencedColumnName: "id_pers")]
    private ?Personne $idPers=null;

    // #[ORM\ManyToOne(inversedBy:'Sousservice')]
    #[ORM\ManyToOne(targetEntity: Service::class)]
    #[ORM\JoinColumn(name: "id_service", referencedColumnName: "id_service")]
    #[Assert\NotBlank(message: 'Merci de choisir un service')]
    private ?Service $idService=null;
//---------------------------------------------------------------------------------------
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;
        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;
        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;
        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;
        return $this;
    }

    public function getNote(): ?float
    {
        return $this->note;
    }

    public function setNote(float $note): self
    {
        $this->note = $note;
        return $this;
    }

    public function getIdEventcateg(): ?String
    {
        return $this->idEventcateg;
    }

    public function setIdEventcateg(String $idEventcateg): self
    {
        $this->idEventcateg = $idEventcateg;
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

    public function getIdService(): ?Service
    {
        return $this->idService;
    }

    public function setIdService(?Service $idService): self
    {
        $this->idService = $idService;
        return $this;
    }


}
