<?php

namespace App\Entity;

use App\Repository\TransactionRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: TransactionRepository::class)]
class Transaction
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'id_trans', type: 'integer')]
    private ?int $id_trans = null;
    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir la valeur du transaction')]
    #[Assert\NotNull(message: 'Merci de remplir la valeur du transaction')]
    private ?float $valeur_trans = null;

    #[ORM\Column(columnDefinition: "ENUM('USD', 'EUR', 'JPY', 'GBP', 'CHF', 'CAD', 'AUD', 'NZD')", nullable: true)]
    private ?string $devis = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $date_trans = null;

    #[ORM\Column(columnDefinition: "ENUM('Square', 'Stripe', 'Amazon Pay', 'Google Pay')", nullable: true)]
    private ?string $mode_trans = null;

    #[ORM\Column]
    private ?float $montant_tot = null;

  //  #[ORM\ManyToOne(targetEntity: Personne::class)]
   // #[ORM\JoinColumn(name: 'user_id', referencedColumnName: 'id_pers', nullable: false)]
   // private ?Personne $user_id = null;
  
   #[ORM\ManyToOne(targetEntity: Personne::class)]
   #[ORM\JoinColumn(name: 'user_id', referencedColumnName: 'id_pers', nullable: false)]
   private ?Personne $user_id = null;
 
    public function getIdTrans(): ?int
    {
        return $this->id_trans;
    }

    public function setIdTrans(int $id_trans): self
    {
        $this->id_trans = $id_trans;

        return $this;
    }

    public function getValeurTrans(): ?float
    {
        return $this->valeur_trans;
    }

    public function setValeurTrans(float $valeur_trans): self
    {
        $this->valeur_trans = $valeur_trans;

        return $this;
    }

    public function getDevis(): ?string
    {
        return $this->devis;
    }

    public function setDevis(string $devis): self
    {
        $this->devis = $devis;

        return $this;
    }

    public function getDateTrans(): ?\DateTimeInterface
    {
        return $this->date_trans;
    }

    public function setDateTrans(\DateTimeInterface $date_trans): self
    {
        $this->date_trans = $date_trans;

        return $this;
    }

    public function getModeTrans(): ?string
    {
        return $this->mode_trans;
    }

    public function setModeTrans(string $mode_trans): self
    {
        $this->mode_trans = $mode_trans;

        return $this;
    }

    public function getMontantTot(): ?float
    {
        return $this->montant_tot;
    }

    public function setMontantTot(float $montant_tot): self
    {
        $this->montant_tot = $montant_tot;

        return $this;
    }



    /**
     * @return float|null
     */
    public function getMontant_tot(): ?float
    {
        return $this->montant_tot;
    }

    /**
     * @param float|null $montant_tot 
     * @return self
     */
    public function setMontant_tot(?float $montant_tot): self
    {
        $this->montant_tot = $montant_tot;
        return $this;
    }
public function __toString(): string
{
    return (string) $this->getIdTrans();
}

    

	/**
	 * @return int|null
	 */
	public function getId_trans(): ?int {
		return $this->id_trans;
	}
	
 

  
 

    public function getUserId(): ?Personne
    {
        return $this->user_id;
    }

    public function setUserId(?Personne $user_id): self
    {
        $this->user_id = $user_id;

        return $this;
    }
}
?>