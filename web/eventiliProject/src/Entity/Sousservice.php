<?php

namespace App\Entity;
//---------------------------------------------------------------------------------------
use Doctrine\ORM\Mapping as ORM;
use App\Repository\SousserviceRepository;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Collections\Collection;
use Doctrine\Common\Collections\ArrayCollection;
//---------------------------------------------------------------------------------------
#[ORM\Entity(repositoryClass: SousserviceRepository::class)]
class Sousservice
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id=null;
//---------------------------------------------------------------------------------------
   #[ORM\Column]
   #[Assert\NotBlank(message: 'Merci de remplir le nom')]
   #[Assert\Length(
    // min: 1,
    max: 30,
    // minMessage: 'Your first name must be at least {{ limit }} characters long',
    maxMessage: 'le nom ne doit pas dépasser {{ limit }} charactères',
)]
    private ?String $nom=null;
//---------------------------------------------------------------------------------------

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir la descritpion')]
    #[Assert\Length(
        // min: 1,
        max: 200,
        // minMessage: 'Your first name must be at least {{ limit }} characters long',
        maxMessage: 'la description ne doit pas dépasser {{ limit }} charactères',
    )]
    private ?String $description=null;
//---------------------------------------------------------------------------------------
    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le prix')]
    // #[Assert\Regex(pattern: '/^[0-9]+(?:\.[0-9]+)?$/', message:"Le prix doit contenir uniquement des chiffres.")]
    private ?float $prix=null;
//---------------------------------------------------------------------------------------
    #[ORM\Column]
    // #[Assert\Regex(pattern: '/^[0-9]*$')]
    private ?float $note=null;
//---------------------------------------------------------------------------------------
    #[ORM\Column]
    private ?String $idEventcateg=null;
//---------------------------------------------------------------------------------------    
    // #[ORM\ManyToOne(inversedBy:'Sousservice')]
    #[ORM\ManyToOne(targetEntity: Personne::class,cascade: ['persist'])]
    #[ORM\JoinColumn(name: "id_pers", referencedColumnName: "id_pers")]
    private ?Personne $idPers=null;
//---------------------------------------------------------------------------------------
    // #[ORM\ManyToOne(inversedBy:'Sousservice')]
    #[ORM\ManyToOne(targetEntity: Service::class)]
    #[ORM\JoinColumn(name: "id_service", referencedColumnName: "id_service")]
    #[Assert\NotBlank(message: 'Merci de choisir un service')]
    private ?Service $idService=null;
//---------------------------------------------------------------------------------------
    #[ORM\OneToMany(targetEntity: Imagess::class,mappedBy:"sousService",orphanRemoval:true,cascade:["persist"])]
    #[Assert\NotBlank(message: 'Merci de choisir au moin une image')]
    private $imagess;
//---------------------------------------------------------------------------------------
    public function __construct()
{
    $this->imagess = new ArrayCollection();
}    
//---------------------------------------------------------------------------------------
    public function getId(): ?int
    {
        return $this->id;
    }
//---------------------------------------------------------------------------------------
    public function getNom(): ?string
    {
        return $this->nom;
    }
//---------------------------------------------------------------------------------------
    public function setNom(string $nom): self
    {
        $this->nom = $nom;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getDescription(): ?string
    {
        return $this->description;
    }
//---------------------------------------------------------------------------------------
    public function setDescription(string $description): self
    {
        $this->description = $description;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getPrix(): ?float
    {
        return $this->prix;
    }
//---------------------------------------------------------------------------------------
    public function setPrix(float $prix): self
    {
        $this->prix = $prix;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getNote(): ?float
    {
        return $this->note;
    }
//---------------------------------------------------------------------------------------
    public function setNote(float $note): self
    {
        $this->note = $note;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getIdEventcateg(): ?String
    {
        return $this->idEventcateg;
    }
//---------------------------------------------------------------------------------------
    public function setIdEventcateg(String $idEventcateg): self
    {
        $this->idEventcateg = $idEventcateg;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getIdPers(): ?Personne
    {
        return $this->idPers;
    }
//---------------------------------------------------------------------------------------
    public function setIdPers(?Personne $idPers): self
    {
        $this->idPers = $idPers;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getIdService(): ?Service
    {
        return $this->idService;
    }
//---------------------------------------------------------------------------------------
    public function setIdService(?Service $idService): self
    {
        $this->idService = $idService;
        return $this;
    }
//---------------------------------------------------------------------------------------    
        /**
     * @return Collection|Imagess[]
     */
    public function getImagess(): Collection
    {
        return $this->imagess;
    }
//---------------------------------------------------------------------------------------
    public function addImagess(Imagess $image): self
    {
        if (!$this->imagess->contains($image)) {
            $this->imagess[] = $image;
            $image->setSousService($this);
        }

        return $this;
    }
//---------------------------------------------------------------------------------------
    public function removeImagess(Imagess $image): self
    {
        if ($this->imagess->contains($image)) {
            $this->imagess->removeElement($image);
            // set the owning side to null (unless already changed)
            if ($image->getSousService() === $this) {
                $image->setSousService(null);
            }
        }
        return $this;
    }
}
