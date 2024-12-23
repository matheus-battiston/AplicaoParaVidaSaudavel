import { useEffect, useState } from "react"
import { useGetWeightHistory } from "../../../api/hooks/useGetWeightHistory/useGetWeightHistory.hook"
import {editCarbs, editHeight, editKcal, editLipids, editProt} from "../../../api/user/edit"
import { editGoal } from "../../../api/user/edit/edit-goal.api"
import { editPhysical } from "../../../api/user/edit/edit-physical.api"
import { editWater } from "../../../api/user/edit/edit-water.api"
import { insertWeight } from "../../../api/user/weight/insert-weight.api"
import { weightHistory } from "../../../api/user/weight/weight-history.api"
import useGlobalUser from "../../../context/user.context"
import { getTodayFormated } from "../../../core/today"
import { Button } from "../button/button.component"
import { Modal } from "../modal/modal.component"
import { RecomendationItens } from "../recomendationItens/recomendationItens.component"
import { Select } from "../select/select.component"
import { WeightInfo } from "../weightInfo/weightInfo.component"
import "./detailedUserInfos.css"

const RECOMENDACOES = "Recomendações"
const AGUA = "Agua"
const KCAL = "Kcal"
const PROTEINAS = "Proteínas"
const CARBOIDRATOS = "Carboidratos"
const GORDURA = "Gordura"
const ML = "ml"
const GRAMA = "g"
const ALTURA = "Altura"
const PESO = "Peso atual"
const KG = "kg"
const CM = "cm"
const IMC = "imc"
const PERDER_GORDURA = "Perder gordura"
const GANHAR_MUSCULO = "Ganhar musculo"
const MANTER_PESO = "Manter peso"
const ATIVIDADE_MODERADA = "Atividade moderada"
const ATIVIDADE_INTENSA = "Atividade intensa"
const ATIVIDADE_BAIXA = "Pouca ou nenhuma atividade"

const MENSAGEM_DEFAULT_META = "Minha meta"
const MENSAGEM_DEFAULT_ATIVIDADE = "Minhas atividades"
const OPTIONS = [{item : PERDER_GORDURA, valor : 'P'}, {item: GANHAR_MUSCULO, valor: 'G'}, {item: MANTER_PESO, valor: 'M'}]
const OPTIONS_ATIVIDADE = [{item: ATIVIDADE_BAIXA, valor: 'P'}, {item: ATIVIDADE_MODERADA, valor:"M"}, {item: ATIVIDADE_INTENSA, valor: 'I'}]

const META = "Meta"
const ATIVIDADE = "Atividade"

export function DetailedUserInfos({reload, setReload, infos}) {
    const [,setUser] = useGlobalUser()

    const [inputWeight, setInputWeight] = useState({date: '', weight: ''})
    const [edit, setEdit] = useState(null)
    const { wightHistoryData, getWeight } = useGetWeightHistory()
    const [showAddWeightModal, setShowAddWeightModal] = useState(false)

    function validate(input){
        if ((!input.match(/^\d+$/) || (input<1))){
            return false
        }

        return true
        
    }

    function dateValidation(date){
        const today = getTodayFormated()
        const todayDate = new Date(today)
        const dateInput = new Date(date)
        if ( dateInput <= todayDate){
            return true
        }
         else {
            return false
         }
    }

    function onChange(event){
        const {value, name} = event.target

        setInputWeight(oldInputWeight => ({...oldInputWeight, [name]:value}))
    }

    function closeModal(){
        setShowAddWeightModal(false)
    }

    function weightModal(){
        setShowAddWeightModal(true)
    }

    async function submitWeightHistory(event){
        event.preventDefault()
        if (validate(inputWeight.weight) && dateValidation(inputWeight.date)) {
            await insertWeight(inputWeight.weight, inputWeight.date)
            setShowAddWeightModal(false)
            setReload(!reload)
            getWeight()
        }        
    }

    async function submit(input, itemName){
        setEdit(null)

        if (validate(input)){
            switch (itemName) {
                case ALTURA:
                    await editHeight(input)
                    break;
                case PROTEINAS:
                    await editProt(input)
                    break;
                case GORDURA:
                    await editLipids(input)
                    break;
                case CARBOIDRATOS:
                    await editCarbs(input)
                    break;
                case KCAL:
                    await editKcal(input)
                    break;
                case PESO:
                    await insertWeight(input, getTodayFormated())
                    break
                case AGUA:
                    await editWater(input)
                    break
                default:
                    break;
            }
        }

        
        setReload(!reload)
        getWeight()
    }

    async function submitSelect(input, itemName){

        switch (itemName) {
            case ATIVIDADE:
                await editPhysical(input)
                break;
        
            case META:
                await editGoal(input)
                break;
            default:
                break;
        }

        setReload(!reload)
    }

    return (
       <>
        {showAddWeightModal === true? 
            <Modal onClose={closeModal} small={true}>
                <form className="form-new-weight" onSubmit={submitWeightHistory}>
                    <div>
                        <span className="label-input-weight">Insira o peso (kg)</span>
                        <input className="weight-input" type="text" name="weight" onChange={onChange}/>
                    </div>

                    <div>
                        <span className="label-input-weight">Data da medição</span>
                        <input className="weight-input" type="date" name="date" onChange={onChange}/>
                    </div>
                    <Button type="second" text="Enviar"/>
                </form>

            </Modal> : null}
        <div className="user-infos">
            <section className="select-goals-activity">
                <Select type={META} options={OPTIONS} mensagemDefault={MENSAGEM_DEFAULT_META} submit={submitSelect}/>
                <Select type={ATIVIDADE} options={OPTIONS_ATIVIDADE} mensagemDefault={MENSAGEM_DEFAULT_ATIVIDADE} submit={submitSelect}/>
            </section>
            <section className="firstInfos">
                <div className="first-infos-left">
                    <h1 className="info-title">Recomendações</h1>
                    <p className="info-description">Recomendações calculadas de acordo com a meta informada, atividades fisicas praticadas e informações sobre o corpo.</p>
                    <div className="waterKcal">
                        <div className="kcalWater border">
                            <RecomendationItens edit={edit === KCAL} itemName={KCAL} itemInfo={infos.caloriasRecomendacao} itemRef={KCAL} editFunc={setEdit} submit={submit}/>
                        </div>

                        <div className="kcalWater border">
                            <RecomendationItens edit={edit === AGUA} itemName={AGUA} itemInfo={infos.aguaRecomendacao} itemRef={ML} editFunc={setEdit} submit={submit}/>
                        </div>
                    </div>

                    <div className="macronutrients border">
                        <div>
                            <RecomendationItens edit={edit === PROTEINAS} itemName={PROTEINAS} itemInfo={infos.proteinasRecomendacao} itemRef={GRAMA} editFunc={setEdit} submit={submit}/>
                        </div>

                        <div>
                            <RecomendationItens edit={edit === CARBOIDRATOS} itemName={CARBOIDRATOS} itemInfo={infos.carboidratosRecomendacao} itemRef={GRAMA} editFunc={setEdit} submit={submit}/>
                        </div>

                        <div>
                           <RecomendationItens edit={edit === GORDURA} itemName={GORDURA} itemInfo={infos.lipidiosRecomendacao} itemRef={GRAMA} editFunc={setEdit} submit={submit}/>
                        </div>
                    </div>
                </div>
                <div className="weight">
                    <h1 className="info-title">Histórico de peso</h1>
                    <div className="weight-history border">
                        <Button type="second" text="Adicionar peso" onClick={weightModal}/>
                        {wightHistoryData?.length ? wightHistoryData?.map(weight => {
                            return <WeightInfo date={weight.data.replaceAll("-", "/")} weight={weight.peso}/>
                        }) : null}
                    </div>
                </div>
            </section>
            <section className="detailed-user-infos">
                <div className="height-weight">
                    <div className="height-weight-infos">
                        <RecomendationItens edit={edit === PESO} itemName={PESO} itemInfo={infos.peso} itemRef={KG} editFunc={setEdit} submit={submit}/>

                    </div>
                    <div className="height-weight-infos">
                        <RecomendationItens edit={edit === ALTURA} itemName={ALTURA} itemInfo={infos.altura} itemRef={CM} editFunc={setEdit} submit={submit}/>
                    </div>

                </div>

                <div className="imc">
                    <p className="imc-title">IMC</p>
                    <p className="imc-value">{infos.imc}</p>
                </div>
            </section>
            <Button onClick={() => setUser(null) } text='Logout' type={'second'}/>
        </div>
       </>
    )
}