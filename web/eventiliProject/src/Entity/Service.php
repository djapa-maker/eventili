<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\ServiceRepository;
use Symfony\Component\Validator\Constraints as Assert;
#[ORM\Entity(repositoryClass: ServiceRepository::class)]
class Service
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idService=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le nom')]
    #[Assert\NotNull(message: 'Merci de remplir le nom')]
    private ?String $nom=null;
//---------------------------------------------------------------------------------------
    public function getIdService(): ?int
    {
        return $this->idService;
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


}
