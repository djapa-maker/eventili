<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\ImgevRepository;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Validator\Constraints as Assert;
// use Vich\UploaderBundle\Mapping\Annotation as Vich;


#[ORM\Entity(repositoryClass: ImgevRepository::class)]
// #[Vich\Uploadable]
class Imgev
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idImgev=null;

    // #[Vich\UploadableField(mapping: 'images_directory', fileNameProperty: 'imagee')]
    private ?File $imageFile = null;


    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir la description')]
    private ?String $imagee=null;


    #[ORM\ManyToOne(targetEntity: Evenement::class)]
    #[ORM\JoinColumn(name: "id_even", referencedColumnName: "id_ev")]
    private ?Evenement $idEven=null;
    

    // public function setImageFile(?File $imageFile = null): void
    // {
    //     $this->imageFile = $imageFile;

    //     if (null !== $imageFile) {
    //         // It is required that at least one field changes if you are using doctrine
    //         // otherwise the event listeners won't be called and the file is lost
    //        // $this->updatedAt = new \DateTimeImmutable();
    //     }
    // }

    // public function getImageFile(): ?File
    // {
    //     return $this->imageFile;
    // }

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
