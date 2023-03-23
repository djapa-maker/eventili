<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\PersonneRepository;
use PhpParser\Node\Name;

#[ORM\Entity(repositoryClass: PersonneRepository::class)]
class Personne
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'id_pers')]
    private ?int $idPers=null;

    #[ORM\Column]
    private ?String $nomPers=null;

    #[ORM\Column]
    private ?String $prenomPers=null;

    #[ORM\Column]
    private ?String $numTel=null;

    #[ORM\Column]
    private ?String $email=null;

    #[ORM\Column]
    private ?String $mdp=null;

    #[ORM\Column]
    private ?String $adresse=null;

    #[ORM\Column]
    private ?String $rib=null;

    #[ORM\Column]
    private ?String $role=null;

    #[ORM\Column]
    private ?String $token=null;

    public function getIdPers(): ?int
    {
        return $this->idPers;
    }

    public function getNomPers(): ?string
    {
        return $this->nomPers;
    }

    public function setNomPers(string $nomPers): self
    {
        $this->nomPers = $nomPers;

        return $this;
    }

    public function getPrenomPers(): ?string
    {
        return $this->prenomPers;
    }

    public function setPrenomPers(string $prenomPers): self
    {
        $this->prenomPers = $prenomPers;

        return $this;
    }

    public function getNumTel(): ?string
    {
        return $this->numTel;
    }

    public function setNumTel(string $numTel): self
    {
        $this->numTel = $numTel;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): self
    {
        $this->mdp = $mdp;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getRib(): ?string
    {
        return $this->rib;
    }

    public function setRib(string $rib): self
    {
        $this->rib = $rib;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;

        return $this;
    }

    public function getToken(): ?string
    {
        return $this->token;
    }

    public function setToken(string $token): self
    {
        $this->token = $token;

        return $this;
    }


}
