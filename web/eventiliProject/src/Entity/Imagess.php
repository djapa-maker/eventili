<?php

namespace App\Entity;
//---------------------------------------------------------------------------------------
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\ImagessRepository;
use Symfony\Component\Validator\Constraints as Assert;
// use Vich\UploaderBundle\Mapping\Annotation as Vich;
// use Symfony\Component\HttpFoundation\File\File;
//---------------------------------------------------------------------------------------
#[ORM\Entity(repositoryClass: ImagessRepository::class)]
// #[Vich\Uploadable]
class Imagess 
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idimgss = null;
//---------------------------------------------------------------------------------------
    #[ORM\Column(nullable:'true')]
    // #[Assert\NotBlank(message: 'Merci de choisir une image')]
     private ?String $img = null;
//---------------------------------------------------------------------------------------
    // #[ORM\ManyToOne(inversedBy:'Imagess')]
    #[ORM\ManyToOne(targetEntity: Sousservice::class)]
    #[ORM\JoinColumn(name: "sous_service", referencedColumnName: "id")]
    // #[ORM\ManyToOne(targetEntity: "App\Entity\SousService", inversedBy: "imagess")]
    // #[ORM\JoinColumn(nullable: false)]
    private ?Sousservice $sousService = null;
//---------------------------------------------------------------------------------------
    // #[Vich\UploadableField(mapping: 'imageFiles', fileNameProperty: 'img')]
    //  private ?File $imageFile=null;
     

    // private ?\DateTimeImmutable $updatedAt = null; 
//---------------------------------------------------------------------------------------
    public function getIdimgss(): ?int
    {
        return $this->idimgss;
    }
//---------------------------------------------------------------------------------------
    public function getImg(): ?string
    {
        return $this->img;
    }
//---------------------------------------------------------------------------------------
    public function setImg(string $img): self
    {
        $this->img = $img;
        return $this;
    }
//---------------------------------------------------------------------------------------
    public function getSousService(): ?Sousservice
    {
        return $this->sousService;
    }
//---------------------------------------------------------------------------------------
    public function setSousService(?Sousservice $sousService): self
    {
        $this->sousService = $sousService;
        return $this;
    }
//---------------------------------------------------------------------------------------
    // public function setImageFile(?File $img1 = null): self
    // {
    //     $this->imageFile = $img1;

    //     if (null !== $img1) {
    //         // It is required that at least one field changes if you are using doctrine
    //         // otherwise the event listeners won't be called and the file is lost
    //         $this->updatedAt = new \DateTimeImmutable('now');
    //     }
    //     return $this;
    // }

    // public function getImageFile(): ?File
    // {
    //     return $this->imageFile;
    // }
}
