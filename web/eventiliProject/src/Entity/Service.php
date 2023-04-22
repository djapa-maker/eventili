<?php

namespace App\Entity;
//---------------------------------------------------------------------------------------
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\ServiceRepository;
use Symfony\Component\Validator\Constraints as Assert;
//---------------------------------------------------------------------------------------
#[ORM\Entity(repositoryClass: ServiceRepository::class)]
#[UniqueEntity(fields: ['nom'], message: 'Ce service existe déjà')]
class Service
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idService=null;
//---------------------------------------------------------------------------------------
    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le nom')]
    #[Assert\NotNull(message: 'Merci de remplir le nom')]
    #[Assert\Length(
        // min: 1,
        max: 30,
        // minMessage: 'Your first name must be at least {{ limit }} characters long',
        maxMessage: 'le nom ne doit pas dépasser {{ limit }} charactères',
    )]
    private ?String $nom=null;
//---------------------------------------------------------------------------------------
    public function getIdService(): ?int
    {
        return $this->idService;
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
}
